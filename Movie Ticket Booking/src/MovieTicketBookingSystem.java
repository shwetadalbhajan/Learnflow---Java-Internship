import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Movie {
    String name;
    String genre;
    ArrayList<String> showtimes;
    boolean[][] seats; // Represents seat availability

    Movie(String name, String genre, ArrayList<String> showtimes, int numRows, int numCols) {
        this.name = name;
        this.genre = genre;
        this.showtimes = showtimes;
        this.seats = new boolean[numRows][numCols];
        initializeSeats();
    }

    private void initializeSeats() {
        // Assume all seats are initially available
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = true;
            }
        }
    }
}

class BookingSystem {
    Map<String, Movie> movies;

    BookingSystem() {
        movies = new HashMap<>();
        initializeMovies();
    }

    private void initializeMovies() {
        ArrayList<String> showtimes1 = new ArrayList<>();
        showtimes1.add("10:00 AM");
        showtimes1.add("2:00 PM");
        showtimes1.add("6:00 PM");
        Movie movie1 = new Movie("Inception", "Sci-Fi", showtimes1, 5, 8);
        movies.put("Inception", movie1);

        ArrayList<String> showtimes2 = new ArrayList<>();
        showtimes2.add("11:30 AM");
        showtimes2.add("4:00 PM");
        showtimes2.add("8:30 PM");
        Movie movie2 = new Movie("The Dark Knight", "Action", showtimes2, 6, 10);
        movies.put("The Dark Knight", movie2);

        ArrayList<String> showtimes3 = new ArrayList<>();
        showtimes3.add("1:00 PM");
        showtimes3.add("5:30 PM");
        showtimes3.add("9:00 PM");
        Movie movie3 = new Movie("Titanic", "Romance", showtimes3, 5, 8);
        movies.put("Titanic", movie3);

        ArrayList<String> showtimes4 = new ArrayList<>();
        showtimes4.add("10:30 AM");
        showtimes4.add("3:15 PM");
        showtimes4.add("7:45 PM");
        Movie movie4 = new Movie("Jurassic Park", "Adventure", showtimes4, 6, 10);
        movies.put("Jurassic Park", movie4);

        // Add more movies
        ArrayList<String> showtimes5 = new ArrayList<>();
        showtimes5.add("12:00 PM");
        showtimes5.add("3:30 PM");
        showtimes5.add("8:00 PM");
        Movie movie5 = new Movie("The Shawshank Redemption", "Drama", showtimes5, 7, 9);
        movies.put("The Shawshank Redemption", movie5);

        ArrayList<String> showtimes6 = new ArrayList<>();
        showtimes6.add("2:30 PM");
        showtimes6.add("6:45 PM");
        showtimes6.add("10:15 PM");
        Movie movie6 = new Movie("The Matrix", "Sci-Fi", showtimes6, 6, 8);
        movies.put("The Matrix", movie6);
    }

    public Map<String, Movie> getMovies() {
        return movies;
    }
}

public class MovieTicketBookingSystem extends JFrame {
    private BookingSystem bookingSystem;

    private JComboBox<String> movieComboBox;
    private JComboBox<String> showtimeComboBox;
    private JButton browseButton;
    private JButton bookButton;
    private JButton cancelButton;
    private JLabel resultLabel;
    private JLabel seatLabel;
    private JPanel seatPanel;
    private JToggleButton[][] seatButtons;

    private Movie currentMovie; // To store the currently selected movie

    public MovieTicketBookingSystem() {
        bookingSystem = new BookingSystem();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Movie Ticket Booking System");

        movieComboBox = new JComboBox<>();
        for (String movie : bookingSystem.getMovies().keySet()) {
            movieComboBox.addItem(movie);
        }

        showtimeComboBox = new JComboBox<>();

        browseButton = new JButton("Browse");
        bookButton = new JButton("Book");
        cancelButton = new JButton("Cancel");

        resultLabel = new JLabel();
        seatLabel = new JLabel("Select seats:");

        seatPanel = new JPanel(); // Panel to contain seat buttons
        seatPanel.setLayout(new GridLayout(0, 8, 5, 5)); // GridLayout for seat buttons

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        controlPanel.add(browseButton);
        controlPanel.add(bookButton);
        controlPanel.add(cancelButton);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMovie = (String) movieComboBox.getSelectedItem();
                Movie movie = bookingSystem.getMovies().get(selectedMovie);

                showtimeComboBox.removeAllItems();
                for (String showtime : movie.showtimes) {
                    showtimeComboBox.addItem(showtime);
                }

                // Initialize seat buttons
                initializeSeatButtons(movie.seats);
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMovie = (String) movieComboBox.getSelectedItem();
                String selectedShowtime = (String) showtimeComboBox.getSelectedItem();
                Movie movie = bookingSystem.getMovies().get(selectedMovie);

                // Get selected seats
                ArrayList<String> selectedSeats = getSelectedSeats(movie.seats);

                // Perform booking logic (e.g., update seat availability)
                if (!selectedSeats.isEmpty()) {
                    updateSeatAvailability(movie.seats, selectedSeats);
                    resultLabel.setText("<html><p style='color:green;'>Booked ticket for " + selectedMovie +
                            " (" + movie.genre + ") at " + selectedShowtime +
                            " for seats: " + String.join(", ", selectedSeats) +
                            "</p></html>");

                    // Store the currently selected movie
                    currentMovie = movie;
                } else {
                    resultLabel.setText("<html><p style='color:red;'>No seats selected. Please choose seats.</p></html>");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSeatSelection();
                resultLabel.setText("<html><p style='color:blue;'>Ticket canceled</p></html>");
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(movieComboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(showtimeComboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(seatLabel)
                                        .addComponent(resultLabel)
                                        .addComponent(controlPanel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(seatPanel))
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(movieComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(showtimeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(seatLabel))
                        .addComponent(resultLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(controlPanel)
                                .addComponent(seatPanel))
        );

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void initializeSeatButtons(boolean[][] seats) {
        int numRows = seats.length;
        int numCols = seats[0].length;

        seatPanel.removeAll(); // Clear the existing seat buttons
        seatButtons = new JToggleButton[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                seatButtons[i][j] = new JToggleButton((i + 1) + "" + (char) ('A' + j));
                seatButtons[i][j].setPreferredSize(new Dimension(40, 40));
                seatButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 12));
                seatPanel.add(seatButtons[i][j]);
            }
        }

        pack();
    }

    private ArrayList<String> getSelectedSeats(boolean[][] seats) {
        ArrayList<String> selectedSeats = new ArrayList<>();

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seatButtons[i][j].isSelected()) {
                    selectedSeats.add((i + 1) + "" + (char) ('A' + j));
                }
            }
        }

        return selectedSeats;
    }

    private void updateSeatAvailability(boolean[][] seats, ArrayList<String> selectedSeats) {
        for (String seat : selectedSeats) {
            int row = Integer.parseInt(seat.substring(0, seat.length() - 1)) - 1;
            int col = seat.charAt(seat.length() - 1) - 'A';
            seats[row][col] = false;
        }
    }

    private void clearSeatSelection() {
        for (int i = 0; i < seatButtons.length; i++) {
            for (int j = 0; j < seatButtons[i].length; j++) {
                seatButtons[i][j].setSelected(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MovieTicketBookingSystem().setVisible(true);
            }
        });
    }
}
