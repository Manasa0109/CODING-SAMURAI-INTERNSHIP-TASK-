import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Question> allQuestions = new ArrayList<>();

        // Step 1: Read questions from the file
        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String questionText = parts[0];
                String[] options = parts[1].split(",");
                int correctOption = Integer.parseInt(parts[2]);
                allQuestions.add(new Question(questionText, options, correctOption));
            }
        } catch (Exception e) {
            System.out.println("Error reading questions file: " + e.getMessage());
            return;
        }

        // Step 2: Quiz loop
        while (true) {
            Collections.shuffle(allQuestions);
            List<Question> quizQuestions = allQuestions.subList(0, Math.min(5, allQuestions.size()));
            int[] userAnswers = new int[quizQuestions.size()];
            int score = 0;

            // Step 3: Ask questions
            for (int i = 0; i < quizQuestions.size(); i++) {
                quizQuestions.get(i).displayQuestion();
                System.out.print("Enter your Answer (1-4): ");
                userAnswers[i] = sc.nextInt();

                if (quizQuestions.get(i).isCorrect(userAnswers[i])) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Wrong Answer");
                }
                System.out.println();
            }

            // Step 4: Show score and feedback
            int percentage = (score * 100) / quizQuestions.size();
            System.out.println("Your Score is: " + score);
            System.out.println("Percentage: " + percentage + "%");

            if (percentage == 100) {
                System.out.println("Excellent!");
            } else if (percentage >= 50) {
                System.out.println("Good job!");
            } else {
                System.out.println("Keep practicing!");
            }

            // Step 5: Review section
            System.out.println("\n--- Review Section ---");
            for (int i = 0; i < quizQuestions.size(); i++) {
                Question q = quizQuestions.get(i);
                System.out.println("Q" + (i + 1) + ": " + q.questionText);
                if (userAnswers[i] >= 1 && userAnswers[i] <= 4) {
                    System.out.println("Your Answer: " + q.options[userAnswers[i] - 1]);
                } else {
                    System.out.println("Your Answer: Invalid Option (" + userAnswers[i] + ")");
                }

                if (q.isCorrect(userAnswers[i])) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Correct Answer: " + q.options[q.correctOption - 1]);
                }
                System.out.println();
            }

            // Step 6: Retry option
            System.out.print("Do you want to retry the quiz? (yes/no): ");
            String choice = sc.next();

            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for playing!");
                break;
            }
        }

        sc.close();
    }
}
