

/**
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: wtite-up
 */
import java.util.Scanner;

/**
 * This class is a grade calculator to calculate a 
 * overall score and a corresponding letter grade and diplay them
 */
public class GradeCalculator {

    public static void main(String[] args) {
        // to store PAs' total score
        double totalScoreOfPAs = 0;
        // to store single PA score
        int PAScore = 0;
        // to store number of PAs
        int numberOfPAs = 0;
        // to store midterm score
        int midterm = 0;
        // to store final score
        int finalScore = 0;
        // to store overall score
        double overallScore = 0;
        // flag to indicate invalid input
        boolean invalid = false;
        // new a Scanner object
        Scanner input = new Scanner(System.in);

        numberOfPAs = input.nextInt();

        if(numberOfPAs == 0){
            invalid = true;
        }

        // for loop to check if there is a invalid input 
        // if found set flag to true
        // and add all PAs to totalScoreOfPAs
        for (int i = 0; i < numberOfPAs; i++){

            PAScore = input.nextInt();
            if (PAScore > 100 || PAScore < 0) {
                invalid = true;
            } else {
                totalScoreOfPAs += PAScore;
            }
            
        }

        midterm = input.nextInt();
        finalScore = input.nextInt();

        // check if flag is true or midterm or final is invalid input 
        // if so stop program and display invalid input
        if( invalid || midterm < 0 || midterm > 100 || finalScore < 0 || finalScore > 100){
            System.out.print("invalid input");
        }else {
            overallScore = totalScoreOfPAs / numberOfPAs * 0.5 + midterm * 0.125 + finalScore * 0.375;
            System.out.print(overallScore + "\n");
            // to determin which letter grade is corresponding to overall score
            if (overallScore >= 90 ){
                System.out.print("A\n");
            } else if (overallScore >= 80 ){
                System.out.print("B\n");
            } else if (overallScore >= 70 ){
                System.out.print("C\n");
            } else if (overallScore >= 60 ){
                System.out.print("D\n");
            } else if (overallScore >= 0) {
                System.out.print("F\n");
            }
        }

    }


}