package com.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.maxxplusapi.controller.PushNotificationPrimaryController;
import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.service.BookingService;
import com.messagehandler.service.MessageHandlerService;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;




@EnableAsync
/**
 * 
 * 
 * @author Joey, Charissa
 * 
 *
 */
@Controller
public class SchedulerService {
	/**
	 * This class triggers the notification service on a schedule: daily, weekly or monthly
	 * This program should be a microservice, a jar file and be put in the machine's startup upon installation
	 * */

	//variables
	@Autowired
	BookingService bookingService;
	@Autowired
	MessageHandlerService messageHandlerService;
	ConfigurableApplicationContext context;
	private static Logger logger = Logger.getLogger(String.valueOf(SchedulerService.class));
	Duration bufferTime = Duration.ofMinutes(15);
	TemporalAmount weeklyInterval = Duration.ofDays(7);
	List<Booking> bookingList = new ArrayList<>();
	/**
	 * Calls the booking service, checks if it is not null and adds the relevant bookings into a list.
	 *
	 * @param frequencyType the frequency in which a notification is meant to be displayed.
	 * @return List of current bookings
	 */
	public List<Booking> getCurrentBookings() {
		List<Booking> currentBookings = new ArrayList<>();
		if (!bookingService.getAllByOrderByEndTimeAsc().isEmpty()) {
			logger.info("Retrieving Current Booking Data...");
			for (com.maxxplusapi.entity.Booking bt : bookingService.getAllByOrderByEndTimeAsc()) {
				Booking bookingToShow = new Booking();
				bookingToShow.setName(bt.getName());
				bookingToShow.setType(bt.getType());
				bookingToShow.setDate(bt.getDate());
				bookingToShow.setStartTime(bt.getStartTime());
				bookingToShow.setEndTime(bt.getEndTime());
				bookingToShow.setRemark(bt.getRemark());
				bookingToShow.setHours(bt.getHours());
				bookingToShow.setProjectId(bt.getProjectId());
				bookingToShow.setActivityId(bt.getActivityId());
				bookingToShow.setId(bt.getId());
				currentBookings.add(bookingToShow);
				logger.info(bookingToShow.toString());
			}
		}else{
			logger.info("Failed to retrieve booking data. The booking service may be null");
		}
		return currentBookings;
	}

	@Scheduled(fixedRate = 10000)
	public void sample() {
		System.out.println("Waiting for notification");
	}

	/**
	 * Execute notification services daily
	 *<p>
	 * javaFxRuntimeStates
	 * State 2: Run the Notification on an already existing thread
	 * State 1: Starts up a JavaFX runtime. The specified Runnable will then be called on the JavaFX
	 * 	 		Application Thread. Required to circumvent IllegalStateException wherein the JavaFX toolkit isn't
	 * 	 		initialised.
	 *</p>
	 * */
	@Scheduled(cron = "0 */15 * ? * *")
	public void pollNotifications() throws IOException, ParseException {
		AtomicInteger javaFxRuntimeState = new AtomicInteger(2);
		List<Booking> bookingList = getCurrentBookings();
		System.out.println(javaFxRuntimeState.get());
			if (javaFxRuntimeState.get() == 1) {
				Platform.setImplicitExit(false);
				Platform.startup(() -> {
					Group root;
					try {
						context = new SpringApplicationBuilder(PushNotificationPrimaryController.class).profiles("scheduler").run();
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource(
										"/primary.fxml"
								)
						);

						loader.setControllerFactory(clazz -> context.getBean(clazz));

						Stage stage = new Stage();
						root = new Group();
						Scene scene = new Scene(root);

						//FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
						Node node = loader.load();

						// Controller stuff
						root.getChildren().add(node);
						stage.setScene(scene);
						javaFxRuntimeState.set(2);
						stage.setOnHidden(evt -> Platform.exit()); // make sure to completely shut down JavaFX when closing the window
						//PushNotificationPrimaryController scontroller = loader.getController();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
			} else {
				Platform.setImplicitExit(false);
				Platform.runLater(() -> {
					Group root;
					try {
						context = new SpringApplicationBuilder(PushNotificationPrimaryController.class).profiles("scheduler").run();
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource(
										"/primary.fxml"
								)
						);

						loader.setControllerFactory(clazz -> context.getBean(clazz));

						Stage stage = new Stage();
						root = new Group();
						Scene scene = new Scene(root);

						// FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
						Node node = loader.load();

						// Controller stuff
						root.getChildren().add(node);
						stage.setScene(scene);
						PushNotificationPrimaryController scontroller = loader.getController();

						// Here we can enable machine time and compare to end time
						// but the error of instantiatiing or initialising Platform or JFX should be fixed

						for (Booking bookingToShow : bookingList) {
							System.out.println("In list of bookings, javaFxRuntimeState = " + javaFxRuntimeState);

							//decision variables
							LocalDateTime yesterday = bookingToShow.getEndTime().atDate(bookingToShow.getDate().minus(1, ChronoUnit.DAYS));

							Duration interval = Duration.between(LocalDateTime.now(), yesterday);

							//console-debug-info
							System.out.println("Calculated Interval: " + interval.toString());

							//Cast to LocalDate & LocalTime into LocalDateTime
							LocalDate date = LocalDate.of(bookingToShow.getDate().getYear(), bookingToShow.getDate().getMonth(), bookingToShow.getDate().getDayOfMonth());
							LocalTime time = LocalTime.of(bookingToShow.getEndTime().getHour(), bookingToShow.getEndTime().getMinute());
							LocalDateTime bookingDateTime = LocalDateTime.of(date, time);
							LocalDateTime bufferedBookingDateTime = applyBufferTimeTo(bookingDateTime, bufferTime);

							//if ((dailyInterval.plus(bufferTime)).is(interval).isZero() && bookingToShow.getEndTime().isBefore(LocalTime.now().plus(bufferTime))) {

							//start notification
							//retrieve the current frequencyType
							String frequencyType = bookingToShow.getType();
							switch(frequencyType){
								case "T":
									System.out.println("In Daily Notification Trigger Check");
									System.out.println(bufferedBookingDateTime.toString());
									//(LocalDate.now().isBefore(bookingToShow.getDate()))
									if((getCurrentLocalDateTime().isAfter(bookingDateTime) ) && getCurrentLocalDateTime().isBefore(bufferedBookingDateTime)){
										scontroller.initData(bookingToShow);
										System.out.println("Before stage show in Daily, javaFxRuntimeState = " + javaFxRuntimeState);
										stage.show();
										System.out.println("After stage show in Daily, javaFxRuntimeState = " + javaFxRuntimeState);
									}//TODO: implement some kind of else statement to make the thread wait or something similar
									break;
								case "W":
									//TODO: Check if it will show the notification on the day the Monthly notification was made
									System.out.println("In Weekly Notification Trigger Check");
									if (getCurrentLocalDateTime().isAfter(bookingDateTime) &&
										getCurrentLocalDateTime().isBefore(bufferedBookingDateTime) &&
										isWeek(LocalDate.now(), bookingToShow.getDate())){
										System.out.println("Before stage show in weekly, javaFxRuntimeState = " + javaFxRuntimeState);
										scontroller.initData(bookingToShow);
										stage.show();
										System.out.println("After stage show in weekly, javaFxRuntimeState = " + javaFxRuntimeState);
									}
									break;
								case "M":
									//TODO: Check if it will show the notification on the day the Monthly notification was made
									System.out.println("In Monthly Notification Trigger Check");
									if (getCurrentLocalDateTime().isAfter(bookingDateTime) &&
											getCurrentLocalDateTime().isBefore(bufferedBookingDateTime) &&
											isMonthElapsed(LocalDate.now(), bookingToShow.getDate())){
										System.out.println("Before stage show in weekly, javaFxRuntimeState = " + javaFxRuntimeState);
										scontroller.initData(bookingToShow);
										stage.show();
										System.out.println("After stage show in weekly, javaFxRuntimeState = " + javaFxRuntimeState);
									}
									System.out.println("In Monthly Notification Trigger Check");
									break;
								default:
									throw new IllegalArgumentException("Invalid frequency type") ;
							}//end of switch case
						}//end of for-each
					} catch (IllegalStateException | IOException stateException) {
						stateException.printStackTrace();
						javaFxRuntimeState.set(1); //change to the mode that initialises the JavaFX runtime
					}//end of catch
				}); //end of runLater() runnable
			} //end of JavaFxRuntime State 2
		}//end of pollNotifications()

	/**
	 * This code automatically sends the data tagged as "N" daily without notification.
	 */
	@Scheduled(cron = "0 30 15 * * *")
	public void runAutomaticDaily() {
		messageHandlerService.handleAddBatchBooking();
	}

	/**
	 * Updates the booking working data just before the program is recalled again
	 */
	@Scheduled(cron = "30 14 * * * *")
	public void refreshBookings(){
		bookingList = getCurrentBookings();
		System.out.print("Booking List updated");
	}

	/**
	 *	Provides a now() instance of LocalDateTime for calculations and comparisons in conditionals
	 *
	 * @return Current LocalDateTime according to machine time
	 */
	public LocalDateTime getCurrentLocalDateTime(){
		return LocalDateTime.now();
	}

	/**
	 * Adds a given amount of time on top of a LocalDateTime instance
	 *
	 * @param ldt current booking time as LocalDateTime
	 * @param bufferTime buffer time period to be added
	 * @return Buffered LocalDateTime
	 */
	public LocalDateTime applyBufferTimeTo(LocalDateTime ldt, TemporalAmount bufferTime){
		return ldt.plus(bufferTime);
	}

	/**
	 * Checks if the number of days since the booking date stored in the DB is a multiple of 7.
	 *
	 * @param today Date today
	 * @param originalBookingDate Date of the booking as stored in the database
	 * @return true if the number of days elapsed is a multiple of 7, otherwise false
	 */
	public boolean isWeek(LocalDate today, LocalDate originalBookingDate){
		Period timeSinceOriginalNotification = Period.between(originalBookingDate, today);
		int daysElapsed = timeSinceOriginalNotification.getDays();
		return (daysElapsed % 7 == 0);
	}

	/**
	 * Projects an expected date for a month or several away from the date stored in the database and checks it against the date today.
	 *<p>
	 *     The method utilises the Calendar class to retrieve what week of the year the dates provided to it reside in.
	 *     It then uses the relative difference in calendar weeks to find the number of weeks to add on top of the original
	 *     booking date. Using a static Period method, plusWeeks(), we can add the correct number of weeks to the original date.
	 *</p>
	 * <p>
	 *     Unlike plusMonths(), plusWeeks(), is able to actually find the correct date a month away and doesn't simply
	 *     change the Calender month's member variable. In other words, it can modify the date and year accordingly.
	 *     Afterwards, we check if the projected date is matched the actual date, which is the date today.
	 *</p>
	 * @param today the current date on the local machine
	 * @param originalBookingDate the date stored in the database when the booking was made/updated
	 * @return true if the date today equals the estimated date, otherwise false
	 */
	public boolean isMonthElapsed(LocalDate today, LocalDate originalBookingDate){
		LocalDate monthDate;

		int dayOriginal = originalBookingDate.getDayOfMonth();
		int monthOriginal = originalBookingDate.getMonthValue();
		int yearOriginal = originalBookingDate.getYear();

		int dayToday = today.getDayOfMonth();
		int monthToday = today.getMonthValue();
		int yearToday = today.getYear();

		//calendar weeks of the year
		Calendar orgCalInstance = Calendar.getInstance();
		Calendar todayCalInstance = Calendar.getInstance();
		orgCalInstance.set(yearOriginal, monthOriginal, dayOriginal);
		todayCalInstance.set(yearToday, monthToday, dayToday);
		int weekOfYearOriginal = orgCalInstance.get(Calendar.WEEK_OF_YEAR);
		int weekOfYearToday = todayCalInstance.get(Calendar.WEEK_OF_YEAR);

		System.out.println("week of year org : "+ weekOfYearOriginal);
		System.out.println("week of year today: "+ weekOfYearToday);

		int numOfWeeksToAdd = 52 - (weekOfYearOriginal - weekOfYearToday);

		try {
			monthDate = addWeeksLocalDate(originalBookingDate, numOfWeeksToAdd);
		}catch (ArithmeticException arithmeticException){
			throw new ArithmeticException("Invalid addition of weeks");
		}

		System.out.println("num of weeks : "+ numOfWeeksToAdd);

		System.out.println("Original Date: "+ originalBookingDate);
		System.out.println("Actual Date: "+ today);
		System.out.println("Expected Date: "+ monthDate);

		return (today.equals(monthDate));
	}

	/**
	 *
	 * @param original
	 * @param weeksToAdd
	 * @return
	 */
	static LocalDate addWeeksLocalDate(LocalDate original, int weeksToAdd){
		return original.plusWeeks(weeksToAdd);
	}
}
