import java.sql.*;
import java.util.Scanner;

public class test2{
  private Scanner scan;
  private Connection connection;

  public test2(){
    scan = new Scanner(System.in);
  }
  
  public void displayLoginWindow(){

    System.out.println("-------------------------Welcome-------------------------");
    System.out.println("Please Enter Your Username:");
    String username = scan.nextLine();
    System.out.println("Please Enter Your Password:");
    String password = scan.nextLine();
    if (connect(username, password)){
      System.out.println("You have succefully connected to database!\n\n");
      displayMenu();
    }
    else {
      System.out.println("Cannot connect to the database! Please try again!");
    }
  }
  
  public boolean connect(String username, String password){
    try{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/moviedb",username, password);
    } catch(Exception e) {
      return false;
    }
    return true;
  }
  
  public int verifyInput(String s){
    try{
      return Integer.parseInt(s);
    }
    catch(NumberFormatException e){
      System.out.print("Please enter a number between 1-8");
      return verifyInput(scan.nextLine());
    }
  }
  
  public void displayMenu() {
    System.out.println("Please choose an option:(BY ENTERING THE NUMBER 1-8) \n");
    System.out.println("  1: Search all the movies featuring a given star.\n");
    System.out.println("  2: Insert a new Star\n.");
    System.out.println("  3: Insert a new Customer.\n");
    System.out.println("  4: Delete a Customer.\n");
    System.out.println("  5: Print MetaData. \n");
    System.out.println("  6: Enter SQL (SELECT/INSERT/UPDATE/DELETE). \n");
    System.out.println("  7: Exit Menu. \n ");
    System.out.println("  8: Exit Program. \n");
    int input = verifyInput(scan.next());

    switch (input) {
    case 1:
      printAllMovies();
      displayMenu();
      break;
    case 2:
      insertStar();
      displayMenu();
      break;
    case 3:
      insertCustomer();
      displayMenu();
      break;
    case 4:
      deleteCustomer();
      displayMenu();
      break;
    case 5:
      printMetaData();
      displayMenu();
      break;
    case 6:
      excecuteSQL();
      displayMenu();
      break;
    case 7:
      displayLoginWindow();
    case 8:
      System.exit(1);
    default: 
      System.out.println("Please Enter Number between 1 to 8!");
      break;
    }
  }
  
  public void printAllMovies(){
    System.out.println("Please choose an option:(BY ENTERING THE NUMBER 1-4)");
    System.out.println("  1: Search by id.");
    System.out.println("  2: Search by firstname.");
    System.out.println("  3: Search by lastname.");
    System.out.println("  4: Search by firstname + lastname.");
    int input = verifyInput(scan.next());
    String sql = "";
    String firstname;
    String lastname;
    switch (input){
      case 1:
        System.out.println("Please Enter the ID:");
        int id = verifyInput(scan.next());
        sql = "s.id = " + id;
        break;
      case 2:
        System.out.println("Please Enter the firstname:(Case sensitive)");
        firstname = scan.next();
        sql = "s.first_name = \"" + firstname + " \" ";
        break;
      case 3:
        System.out.println("Please Enter the lastname:(Case sensitive)");
        lastname = scan.next();
        sql = "s.last_name = \"" + lastname + " \" ";
        break;
      case 4:
        System.out.println("Please Enter the firstname + lastname:(Sepearte by space, Case sensitive)");
        firstname = scan.next();
        lastname = scan.next();
        sql = "s.first_name = \"" + firstname + "\" AND s.last_name = \""+ lastname+ "\""; 
        break;
      default:
        System.out.println("Please enter number between 1-4!");
        printAllMovies();
    }
    try{
      Statement select = connection.createStatement();
      ResultSet result = select.executeQuery("select * from movies m where m.id in (select sm.movie_id from stars_in_movies sm where sm.star_id in (select s.id from stars s where "+ sql + " ))");
      if (!result.next() ) {
      System.out.println("Cannot find any record, please check the input. \n\n");
      }
      while (result.next()){
      System.out.println("Id = " + result.getInt(1));
      }
    } catch(SQLException e){
      System.out.println("Cannot execute, please check the input.");
    }
  }
  
  public void insertStar(){
    String firstname;
    String lastname;
    String[] splitedName;
    String sql;
    System.out.println("Please enter the name of the star: (firstname lastname)");
    String name = scan.nextLine();
    if (name.contains(" ")){
      splitedName = name.split(" ");
      firstname = splitedName[0];
      lastname = splitedName[1];
      }
    else{
      firstname = "";
      lastname = name;
    }
    System.out.println("Please enter the birthday of the star(xxxx-xx-xx, Not Required)");
    String birthday = scan.nextLine();
    System.out.println("Please enter the photo URL (Noe Required)");
    String url = scan.nextLine();
    sql = "INSERT INTO stars (first_name, last_name, dob, photo_url) VALUES (\'"+firstname + "\' , \'" + lastname + "\', \'" + birthday +"\' , \'" + url + "\');";
    try{
        Statement select = connection.createStatement();
        int result = select.executeUpdate(sql);
        System.out.println("The star has been added to database!");
    }
    catch(SQLException e){
      System.out.println("Cannot add star, please check the input.");
    }

  }
  
  public void insertCustomer(){
    
  }
  
  public void deleteCustomer(){
    
  }
  
  public void printMetaData(){
    
  }
  
  public void excecuteSQL(){
    
  }
  
  
  
  public static void main(String[] arg) throws Exception{
        // Incorporate mySQL driver
    test2 t2 = new test2();
    while (true){
      t2.displayLoginWindow();
      System.out.println("Press q to quit, press any other value to continue");
      String option = t2.scan.nextLine();
      // System.out.println("options is " + option + " equals ? " + （option == "q"）);
      if (option.equals("q")){
        System.exit(1);
      }
    }

  }
}