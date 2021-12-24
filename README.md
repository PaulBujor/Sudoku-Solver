# Sudoku-Solver
Sudoku Solver in Java using backtracking algortihm.

Works for 9x9 sudokus, could be updated to any size.


# Variables
## Presence Matrix
The Presence Matrix is used to store the number of apparitions of each number in the matrix on 3 different levels:  
    - [0] each line  
    - [1] each column  
    - [2] each 3x3 block  
    
Each of these 'layers' store the presence in lines:  
    - [0] line 1 corresponds to line 1 in the presMat  
    - [1] column 1 corresponds to line 1 in the presMat  
    - [2] first 3x3 block corresponds to line 1 in the presMat  

If any value in this presenceMatrix is over 1 that means that the conditions for a completed Sudoku is not met, since there are multiple apparitions of the same number on the same line/column/block. This is verified by the ```boolean valid``` method

For example, if we had the following Sudoku:

      1 1 3 | 4 5 6 | 7 8 9  
      0 0 0 | 0 0 0 | 0 0 0  
      the rest of the elements are 0
      notice the double apparition of 1
      
layer 0 (lines) would be:

        0 1 2 3 4 5 6 7 8
        _________________
      0|2 0 1 1 1 1 1 1 1
      1|0 0 0 0 0 0 0 0 0
      2|0 0 0 0 0 0 0 0 0
        ...
      
layer 1 (columns) would be:  

        0 1 2 3 4 5 6 7 8
        _________________
      0|1 0 0 0 0 0 0 0 0
      1|0 1 0 0 0 0 0 0 0
      2|0 0 1 0 0 0 0 0 0
       ...
 
layer 2 (blocks) would be:  

        0 1 2 3 4 5 6 7 8
        _________________
      0|2 0 1 0 0 0 0 0 0
      1|0 0 0 1 1 1 0 0 0
      2|0 0 0 0 0 0 1 1 1
       ...
 
## K
k is the current position in the sudoku matrix, as following:
1  2  3  | 4  5  6  | 7  8  9  
10 11 12 | 13 14 15 | 16 17 18 ...  

this is then used to get the position of the current element to be used in the presenceMatrix:  
  - line = k/9
  - column = k%9
  - block = (k/27)*3 + (k%9)/3 = the line number is divided by 27 (3 lines), and then multiplied by 3 to get the line of the block, and the column number is divided by 3 to get the number of the block column
    - before calculation:  
      
      
          / 0 1 2   3 4 5   6 7 8  
          0       |       |  
          1       |       |  
          2       |       |  
           _______________________  
          3       |       |  
          4       |       |  
          5       |       |  
           _______________________  
          6       |       |  
          7       |       |  
          8       |       |  
         
         
    - after calculation:  
      
      
          / 0 0 0   1 1 1   2 2 2  
          0       |       |  
          0   0   |   1   |   2  
          0       |       |  
           _______________________  
          1       |       |  
          1   3   |   4   |   5  
          1       |       |  
           _______________________  
          2       |       |  
          2   6   |   7   |   8  
          2       |       |  
      

## canModify
canModify is a matrix created when loading the uncompleted sudoku from a file to store which values shouldnt be touched by the algorithm

# Algorithm
The algorithm works by trying every value in the modifiable spaces until it finds a solution. Once it the program fills in a number, it goes to the next position to try the numbers. If all the numbers (1-9) cause the valid method to return false, the program goes to the previous position to try a new number, and then goes to the next position again, re-trying every number. 

When a number is tried, its appearance is added to the presence matrix, and when it is removed its presence is removed.

A solution is found when k reaches 80 (last position) and the valid method return true (meaning the last piece to be completed is valid, and therefore the sudoku is completed), which works when the last position is empty and modifiable, or when k reaches 81, which would mean that the program got through all the positions. This does cause an exception, but at this point the algorithm stops.  

If a solution is not found, the program will eventually make it back to position -1 with the solutionFound boolean still false.
