package com.spring.mysql.Ticket_Booking_System;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mysql.Ticket_Booking_System.Entity.Booking;
import com.spring.mysql.Ticket_Booking_System.Entity.Movie;
import com.spring.mysql.Ticket_Booking_System.Entity.Payment;
import com.spring.mysql.Ticket_Booking_System.Entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

@SpringBootApplication
public class TicketBookingSystemApplication {

	private static final String USER_BASE_URL = "http://localhost:8080/demo";
	private static final String MOVIE_BASE_URL = "http://localhost:8080/movies";
	private static final String BOOKING_BASE_URL = "http://localhost:8080/bookings";
	private static final String PAYMENT_BASE_URL = "http://localhost:8080/payments";
	private static final HttpClient client = HttpClient.newHttpClient();

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingSystemApplication.class, args);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n===== Movie Ticket Booking System =====");
			System.out.println("1. Manage Users");
			System.out.println("2. Manage Movies");
			System.out.println("3. Book Ticket");
			System.out.println("4. Cancel Ticket");
			System.out.println("5. View Bookings");
			System.out.println("6. Search Movie by Name");
			System.out.println("7. View Show Timings");
			System.out.println("8. Payment & Billing");
			System.out.println("9. Exit Menu (but keep server running)");
			System.out.println("0. Exit Application");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();

			switch (choice) {
				case 1 -> userMenu(scanner);
				case 2 -> movieMenu(scanner);
				case 3 -> bookTicketCLI(scanner);
				case 4 -> cancelTicketCLI(scanner);
				case 5 -> bookingMenu(scanner);
				case 6 -> searchMovieCLI(scanner);
				case 7 -> viewShowTimesCLI(scanner);
				case 8 -> handlePayments(scanner);
				case 9 -> System.out.println("Returning to menu... (server still running).");
				case 0 -> {
					System.out.println("Exiting Application. Goodbye!");
					System.exit(0);
				}
				default -> System.out.println("Invalid choice, please try again!");
			}
		}
	}

	// ================= USER MENU =================
	private static void userMenu(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		while (true) {
			System.out.println("\n--- User Management ---");
			System.out.println("1. Add User");
			System.out.println("2. Update User");
			System.out.println("3. Delete User");
			System.out.println("4. Show All Users");
			System.out.println("5. Back to Main Menu");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> {
					System.out.print("Enter Name: ");
					String name = scanner.nextLine();
					System.out.print("Enter Email: ");
					String email = scanner.nextLine();
					System.out.print("Enter Phone: ");
					String phone = scanner.nextLine();
					System.out.print("Enter Password: ");
					String password = scanner.nextLine();

					String url = USER_BASE_URL + "/add?name=" + name + "&email=" + email + "&phone=" + phone + "&password=" + password;
					String response = restTemplate.postForObject(url, null, String.class);
					System.out.println("✅ User Added: " + response);
				}
				case 2 -> {
					System.out.print("Enter User ID to Update: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter New Name: ");
					String name = scanner.nextLine();
					System.out.print("Enter New Email: ");
					String email = scanner.nextLine();
					System.out.print("Enter New Phone: ");
					String phone = scanner.nextLine();
					System.out.print("Enter New Password: ");
					String password = scanner.nextLine();

					String url = USER_BASE_URL + "/" + id + "?name=" + name + "&email=" + email + "&phone=" + phone + "&password=" + password;
					restTemplate.put(url, null);
					System.out.println("✅ User Updated.");
				}
				case 3 -> {
					System.out.print("Enter User ID to Delete: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					String url = USER_BASE_URL + "/" + id;
					restTemplate.delete(url);
					System.out.println("✅ User Deleted with ID: " + id);
				}
				case 4 -> {
					String url = USER_BASE_URL + "/all";
					User[] users = restTemplate.getForObject(url, User[].class);

					System.out.println("👤 All Users:");
                    assert users != null;
                    for (User u : users) {
						System.out.println(" - ID: " + u.getId() + ", Name: " + u.getName() + ", Email: " + u.getEmail());
					}

				}
				case 5 -> { return; }
				default -> System.out.println("Invalid choice, please try again!");
			}
		}
	}

	// ================= MOVIE MENU =================
	private static void movieMenu(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		while (true) {
			System.out.println("\n--- Movie Management ---");
			System.out.println("1. Add Movie");
			System.out.println("2. Update Movie");
			System.out.println("3. Delete Movie");
			System.out.println("4. Show All Movies");
			System.out.println("5. Back to Main Menu");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> {
					System.out.print("Enter Title: ");
					String title = scanner.nextLine();
					System.out.print("Enter Director: ");
					String director = scanner.nextLine();
					System.out.print("Enter Duration (e.g., 2h 15m): ");
					String duration = scanner.nextLine();
					System.out.print("Enter Release Date: ");
					String releaseDate = scanner.nextLine();

					String url = MOVIE_BASE_URL + "/add?title=" + title + "&director=" + director + "&duration=" + duration + "&releaseDate=" + releaseDate;
					String response = restTemplate.postForObject(url, null, String.class);
					System.out.println("✅ Movie Added: " + response);
				}
				case 2 -> {
					System.out.print("Enter Movie ID to Update: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter New Title: ");
					String title = scanner.nextLine();
					System.out.print("Enter New Director: ");
					String director = scanner.nextLine();
					System.out.print("Enter New Duration: ");
					String duration = scanner.nextLine();
					System.out.print("Enter New Release Date: ");
					String releaseDate = scanner.nextLine();

					String url = MOVIE_BASE_URL + "/" + id + "?title=" + title + "&director=" + director + "&duration=" + duration + "&releaseDate=" + releaseDate;
					restTemplate.put(url, null);
					System.out.println("✅ Movie Updated.");
				}
				case 3 -> {
					System.out.print("Enter Movie ID to Delete: ");
					int id = scanner.nextInt();
					scanner.nextLine();

					String url = MOVIE_BASE_URL + "/" + id;
					restTemplate.delete(url);
					System.out.println("✅ Movie Deleted with ID: " + id);
				}
				case 4 -> {
					String url = MOVIE_BASE_URL + "/all";
					Movie[] movies = restTemplate.getForObject(url, Movie[].class);

					System.out.println("🎬 All Movies:");
                    assert movies != null;
                    for (Movie m : movies) {
						System.out.println(" - ID: " + m.getId() + ", Title: " + m.getTitle());
					}

				}
				case 5 -> { return; }
				default -> System.out.println("Invalid choice, please try again!");
			}
		}
	}

	// ================= BOOK TICKET =================
	private static void bookTicketCLI(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		try {
			System.out.print("Enter User ID: ");
			int userId = scanner.nextInt();
			System.out.print("Enter Movie ID: ");
			int movieId = scanner.nextInt();
			System.out.print("Enter Number of Seats: ");
			int numberOfSeats = scanner.nextInt();
			scanner.nextLine();
			System.out.print("Enter Show Time (e.g., 7:30 PM): ");
			String showTime = scanner.nextLine();

			String url = BOOKING_BASE_URL + "/add?userId=" + userId +
					"&movieId=" + movieId +
					"&numberOfSeats=" + numberOfSeats +
					"&showTime=" + showTime;

			String response = restTemplate.postForObject(url, null, String.class);

			// Parse JSON and pretty print
			ObjectMapper mapper = new ObjectMapper();
			JsonNode booking = mapper.readTree(response);

			System.out.println("\n✅ Booking Confirmed!");
			System.out.println("──────────────────────────────");
			System.out.println("📌 Booking ID   : " + booking.get("id").asInt());
			System.out.println("👤 User ID      : " + booking.get("userId").asInt());
			System.out.println("🎬 Movie ID     : " + booking.get("movieId").asInt());
			System.out.println("💺 Seats        : " + booking.get("numberOfSeats").asInt());
			System.out.println("⏰ Show Time    : " + booking.get("showTime").asText());
			System.out.println("🕒 Booking Time : " + booking.get("bookingTime").asText());
			System.out.println("──────────────────────────────");

		} catch (Exception e) {
			System.out.println("⚠️ Error while booking ticket: " + e.getMessage());
		}
	}


	// ================= CANCEL BOOKING =================
	private static void cancelTicketCLI(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		System.out.print("Enter Booking ID to cancel: ");
		int bookingId = scanner.nextInt();

		String url = BOOKING_BASE_URL + "/" + bookingId;
		restTemplate.delete(url);

		System.out.println("❌ Booking Cancelled with ID: " + bookingId);
	}

	// ================= BOOKING MENU =================
	private static void bookingMenu(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		while (true) {
			System.out.println("\n--- Booking Management ---");
			System.out.println("1. View All Bookings");
			System.out.println("2. View Bookings by User ID");
			System.out.println("3. View Bookings by Movie ID");
			System.out.println("4. Back to Main Menu");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();

			switch (choice) {
				case 1 -> {
					String url = BOOKING_BASE_URL + "/all";
					Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
					System.out.println("📋 All Bookings:");
					if (bookings != null && bookings.length > 0) {
						for (Booking b : bookings) {
							System.out.println(" - ID: " + b.getId() +
									", User ID: " + b.getUserId() +
									", Movie ID: " + b.getMovieId() +
									", Seats: " + b.getNumberOfSeats() +
									", ShowTime: " + b.getShowTime() +
									", Booking Time: " + b.getBookingTime());
						}
					} else {
						System.out.println("   No bookings found.");
					}
				}
				case 2 -> {
					System.out.print("Enter User ID: ");
					int userId = scanner.nextInt();
					String url = BOOKING_BASE_URL + "/user/" + userId;
					Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
					System.out.println("📋 Bookings for User " + userId + ":");
					if (bookings != null && bookings.length > 0) {
						for (Booking b : bookings) {
							System.out.println(" - ID: " + b.getId() +
									", Movie ID: " + b.getMovieId() +
									", Seats: " + b.getNumberOfSeats() +
									", ShowTime: " + b.getShowTime() +
									", Booking Time: " + b.getBookingTime());
						}
					} else {
						System.out.println("   No bookings found for User " + userId);
					}
				}
				case 3 -> {
					System.out.print("Enter Movie ID: ");
					int movieId = scanner.nextInt();
					String url = BOOKING_BASE_URL + "/movie/" + movieId;
					Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
					System.out.println("🎬 Bookings for Movie " + movieId + ":");
					if (bookings != null && bookings.length > 0) {
						for (Booking b : bookings) {
							System.out.println(" - ID: " + b.getId() +
									", User ID: " + b.getUserId() +
									", Seats: " + b.getNumberOfSeats() +
									", ShowTime: " + b.getShowTime() +
									", Booking Time: " + b.getBookingTime());
						}
					} else {
						System.out.println("   No bookings found for Movie " + movieId);
					}
				}
				case 4 -> {
					return;
				}
				default -> System.out.println("Invalid choice, please try again!");
			}
		}
	}



	// ================= SEARCH MOVIE =================
	private static void searchMovieCLI(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		System.out.print("Enter movie name to search: ");
		scanner.nextLine(); // consume newline
		String name = scanner.nextLine();

		String url = MOVIE_BASE_URL + "/search?name=" + name;
		Movie[] movies = restTemplate.getForObject(url, Movie[].class);

		System.out.println("🔍 Search Results for \"" + name + "\":");
		if (movies != null && movies.length > 0) {
			for (Movie m : movies) {
				System.out.println(" - ID: " + m.getId() +
						", Title: " + m.getTitle() +
						", Director: " + m.getDirector() +
						", Duration: " + m.getDuration() +
						", Release Date: " + m.getReleaseDate());
			}
		} else {
			System.out.println("   No movies found with name \"" + name + "\".");
		}
	}


	// ================= SHOWTIME =================
	private static void viewShowTimesCLI(Scanner scanner) {
		RestTemplate restTemplate = new RestTemplate();

		System.out.print("Enter Movie ID: ");
		int movieId = scanner.nextInt();
		scanner.nextLine(); // consume newline

		String url = "http://localhost:8080/movie/shows/" + movieId;
		String[] showtime = restTemplate.getForObject(url, String[].class);

		System.out.println("🎬 Showtime for Movie ID " + movieId + ":");
        assert showtime != null;
        for (String time : showtime) {
			System.out.println(" - " + time);
		}

	}


	// ================== PAYMENT & BILLING MENU ==================
	private static void handlePayments(Scanner scanner) {
		while (true) {
			System.out.println("\n===== Payment & Billing Menu =====");
			System.out.println("1. Make Payment");
			System.out.println("2. View All Payments");
			System.out.println("3. Get Payment by ID");
			System.out.println("4. Get Payments by Booking ID");
			System.out.println("5. Get Payments by Status");
			System.out.println("6. Back to Main Menu");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // buffer clear

			try {
				switch (choice) {
					case 1: // make payment
						System.out.print("Enter Booking ID: ");
						int bookingId = scanner.nextInt();
						scanner.nextLine();

						System.out.print("Enter Payment Method (UPI/Card/NetBanking): ");
						String method = scanner.nextLine();

						HttpRequest makePaymentReq = HttpRequest.newBuilder()
								.uri(URI.create(PAYMENT_BASE_URL + "/make/" + bookingId + "?paymentMethod=" + method))
								.POST(HttpRequest.BodyPublishers.noBody())
								.build();

						HttpResponse<String> makePaymentResp = client.send(makePaymentReq, HttpResponse.BodyHandlers.ofString());
						System.out.println("✅ Payment Response: " + makePaymentResp.body());
						break;

					case 2: // all payments
						Payment[] payments = new RestTemplate().getForObject(PAYMENT_BASE_URL + "/all", Payment[].class);
						System.out.println("📋 All Payments:");
						if (payments != null && payments.length > 0) {
							for (Payment p : payments) {
								System.out.println(" - ID: " + p.getId() +
										", Booking ID: " + p.getBookingId() +
										", Method: " + p.getPaymentMethod() +
										", Amount: " + p.getAmount() +
										", Status: " + p.getStatus());
							}
						} else {
							System.out.println("   No payments found.");
						}
						break;

					case 3: // by ID
						System.out.print("Enter Payment ID: ");
						long paymentId = scanner.nextLong();

						Payment payment = new RestTemplate().getForObject(PAYMENT_BASE_URL + "/" + paymentId, Payment.class);
						if (payment != null) {
							System.out.println("💳 Payment Details:");
							System.out.println(" - ID: " + payment.getId() +
									", Booking ID: " + payment.getBookingId() +
									", Method: " + payment.getPaymentMethod() +
									", Amount: " + payment.getAmount() +
									", Status: " + payment.getStatus() +
									", Time: " + payment.getPaymentDate());
						} else {
							System.out.println("   Payment not found!");
						}
						break;

					case 4: // by booking
						System.out.print("Enter Booking ID: ");
						long bId = scanner.nextLong();

						Payment[] paymentsByBooking = new RestTemplate().getForObject(PAYMENT_BASE_URL + "/booking/" + bId, Payment[].class);
						System.out.println("📋 Payments for Booking ID " + bId + ":");
						if (paymentsByBooking != null && paymentsByBooking.length > 0) {
							for (Payment p : paymentsByBooking) {
								System.out.println(" - ID: " + p.getId() +
										", Method: " + p.getPaymentMethod() +
										", Amount: " + p.getAmount() +
										", Status: " + p.getStatus() +
										", Time: " + p.getPaymentDate());
							}
						} else {
							System.out.println("   No payments found for Booking " + bId);
						}
						break;

					case 5: // by status
						System.out.print("Enter Status (SUCCESS/FAILED/PENDING): ");
						String status = scanner.nextLine();

						Payment[] paymentsByStatus = new RestTemplate().getForObject(PAYMENT_BASE_URL + "/status/" + status, Payment[].class);
						System.out.println("📋 Payments with Status " + status + ":");
						if (paymentsByStatus != null && paymentsByStatus.length > 0) {
							for (Payment p : paymentsByStatus) {
								System.out.println(" - ID: " + p.getId() +
										", Booking ID: " + p.getBookingId() +
										", Method: " + p.getPaymentMethod() +
										", Amount: " + p.getAmount() +
										", Time: " + p.getPaymentDate());
							}
						} else {
							System.out.println("   No payments found with status " + status);
						}
						break;

					case 6:
						return;

					default:
						System.out.println("Invalid choice! Try again.");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
