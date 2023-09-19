
/**
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: wtite-up, Java StringBuilder API
 */
import java.util.Scanner;

/**
 * This class is a password strengths checker and give suggest password combinations if applicable
 */
public class PasswordSecurity {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // print prompt ask user to input password
        System.out.print("Please enter a password: ");
        // store the user input to variable password
        String password = input.nextLine();
	
        int length = password.length();
	
        int numbers = 0;
        int uppercase = 0;
        int lowercase = 0;
        int symbols = 0;

        int strengths = 0;

        
        
        //check if the password is less than 8 characters
        if(length < 8) {
            System.out.print("Password is too short\n");
            return;
        }

        // loop for every sigle characters in password
        for (int i = 0; i < length; ++i ){
            char passwordChar = password.charAt(i);
            // count how many numbers in the password
            if (passwordChar >= 48 && passwordChar <= 57){
                numbers++;
                continue;
            }
            // count how many uppercase characters in the password
            if (passwordChar >= 65 && passwordChar <= 90){
                uppercase++;
                continue;
            }
            // count how many lowercase characters in the password
            if (passwordChar >= 97 && passwordChar <= 122){
                lowercase++;
                continue;
            }
            // count how many symbols in the password
            if ((passwordChar >= 33 && passwordChar <= 47) || 
                (passwordChar >= 58 && passwordChar <= 64) || 
                (passwordChar >= 91 && passwordChar <= 96) ||
                (passwordChar >= 123 && passwordChar <= 126))
            {
                    
                symbols++;
                continue;
            }

        }

	
	// determine password strengths grade
        if( numbers != 0){
            strengths++;
        }
        if( uppercase != 0){
            strengths++;
        }
        if( lowercase != 0){
            strengths++;
        }
        if( symbols != 0){
            strengths++;
        }

        switch (strengths){
            case 1:
            System.out.print("Password strength: very weak\n");
            break;

            case 2:
            System.out.print("Password strength: weak\n");
            break;

            case 3:
            System.out.print("Password strength: medium\n");
            break;

            case 4:
            System.out.print("Password strength: strong\n");
            return;
        }
        
        StringBuilder suggestPassword = new StringBuilder(password);

        if (lowercase + uppercase < 2){
            // apply rule 1
            suggestPassword.insert(0, "Cse");

        } else if (lowercase == 0){
            // apply rule 2
            int indexOfFirst = 0;
            for (int i = 0; i < length; ++i){
                if(password.charAt(i) >= 65 && password.charAt(i) <= 90){
                    
                    indexOfFirst = i;
                    break;
                }
            }
            char newChar = Character.toLowerCase(password.charAt(indexOfFirst));
            suggestPassword.setCharAt(indexOfFirst, newChar);
            
        } else if (uppercase == 0){
            // apply rule 3
            char highestVaule = 'a';
            int indexOfHighest = 0;
            
            for (int i = 0; i < length; ++i){
                if(password.charAt(i) >= 97 && password.charAt(i) <= 122){
                    if(password.charAt(i) >= highestVaule){
                        highestVaule = password.charAt(i);
                        indexOfHighest = i;
                    }
                }
            }
            char newChar = Character.toUpperCase(password.charAt(indexOfHighest));
            suggestPassword.setCharAt(indexOfHighest, newChar);

        }

        int spLength = suggestPassword.length();
        if (numbers == 0){
            // apply rule 4
            int k = length % 10;
        
            
            for (int i = 4; i < suggestPassword.length(); i += 5){
                suggestPassword.insert(i, k);
            }
            

            if (spLength % 4 == 0){
                suggestPassword.append(k);
            }
        }

        if (symbols == 0){
            // apply rule 5
            suggestPassword.append("@!");
        }
        
        System.out.print("Here is a suggested stronger password: " + suggestPassword + "\n");

    }
}
