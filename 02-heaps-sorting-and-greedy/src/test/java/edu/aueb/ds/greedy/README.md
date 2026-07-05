# Έλεγχος Υλοποίησης Αλγορίθμων Greedy

Σε αυτόν τον φάκελο θα βρείτε τον κώδικα για να ελέγξετε την υλοποίηση των αλγορίθμων `Greedy` και `GreedyDecreasing`.

## Εκτέλεση του Test

Για να ελέγξετε την υλοποίησή σας, μπορείτε να εκτελέσετε την `main` μέθοδο του αρχείου `GreedyTestMain.java`. Το test χρησιμοποιεί το αρχείο `artifacts/greedy_test_cases/folders.txt` ως είσοδο.

### Οδηγίες Εκτέλεσης από τη Γραμμή Εντολών

1.  Βεβαιωθείτε ότι βρίσκεστε στον κεντρικό φάκελο του project (`DS_exercise_2`).
2.  Μεταγλωττίστε ολόκληρο τον κώδικα του project (εφαρμογή και tests) εκτελώντας την παρακάτω εντολή.
    ```bash
    javac -d out/production/ $(find src -name "*.java")
    ```
3.  Εκτελέστε τον κώδικα ελέγχου με την παρακάτω εντολή:
    ```bash
    java -cp out/production/ edu.aueb.ds.greedy.GreedyTestMain
    ```
