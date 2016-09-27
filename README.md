from http://comet.lehman.cuny.edu/owen/teaching/2016fa/projects.html

# OSProj2

For this project, you will write a program to simulate how page tables and pages work in memory. The main method will be inside a class called OS, which represents an operating system. This class will contain a page table and an array of Page objects, which represent pages in memory. We will assume there is only one process/page table/set of pages for this project.

CMP 426 Part 1; CMP 697 Part 1a

For this part, you will start writing the two classes: OS, which represents an operating system; and Page, which represent a page in memory. You may change the method parameter names below, but not the names of the methods themselves (since they are used for testing in Mimir).

The class Page should contain:

an instance variable to store its virtual page number
an instance variable that is a byte array and represents the data stored in that page (so the byte in position 0 in the array would be the first byte of data stored in the page, etc.), and
a method called getData(int offset) which returns the byte at position offset in the data array. This method should throw an exception with the message "invalid offset" if offset is invalid (i.e. if it is a negative number or greater than the size of the data array).
The class OS should contain:

an array of Page objects, representing the computer's physical memory. In Part 1, the size of the array will always be 8, but in parts 2 and 3, the size can vary. The index of a Page in this array is considered its physical address.
for some kind of data structure representing a page table. The simplest option is to use an array, but you can use a more complex data structure if you prefer. This data structure should allow the user to look up the physical page number (ppn), given the virtual page number (vpn). It will be accessed and tested using the following method.
a method called getPPN(int vpn), which looks up the virtual page number (vpn) in the page table and returns the corresponding ppn (physical page number).
a method called getDataAtVirtAddress(int virtAddress), which returns the byte stored at virtual address virtAddress. In Part 1, we will only use the last 7 bits of virtAddress to represent the address. The last 4 bits will represent the offset and the next 3 bits will represent the virtual page number. In parts 2 and 3, the size of the offset and virtual page number can vary. To find the byte to return, you must consult the page table to determine the corresponding physical page number, find that Page object, and return the byte in its data array at index corresponding to the offset. You should use bit manipulation as much as possible in doing the calculations.
a constructor OS(String filename) that loads page table and page data from the file called filename into the corresponding data structure and objects. The file will have the following format:
first line contains two integers, separated by a space, representing the number of pages (numPages) and the number of bytes in each page (numBytes). For Part 1, all files will begin with "8 16".
the next numPages lines represent the entries of the page table. Each line will be in the format "vpn->ppn" (i.e. "5->6").
the next numPages lines will contain numBytes bytes of data for each page.
An example of a data file for Part 1 is here. It is encoded using UTF-8, so each character represents 1 byte.
CMP 426 Part 2; CMP 697 Part 1b

Modify your code so that it can accept any number of pages and bytes of data per page, with the constraint that the corresponding virtual address size will be less than or equal to 32 bits (= size of an int in Java). The number of pages and number of bytes per page will always be given at the top of the data file.

CMP 426 Part 3; CMP 697 Part 2

For this part, you will create a (simulated) Translation Lookaside Buffer (TLB) that uses the random replacement policy. There are no Mimir test cases, so you have more freedom in how you implement this part.

You can simulate the TLB however you would like, but you should be able to specify the number of entries it holds. One way is to create a 2D array as an instance variable of your OS class, where the size of the array is (number of entries in TLB) x 2. If your array name is tlb, then tlb[i][0] would be the virtual page number at entry i and tlb[i][1] would be the corresponding physical page number. Or, if you are planning to do the next part, you could make a new class to represent an entry in the TLB, and use an array (or some other data structure) of this class as the TLB.

Once all of the TLB entries are filled for the first time, the entry to be replaced should be chosen at random.

To simulate it taking more time to find the physical address if the virtual address is not in the TLB, add a delay for this case using TimeUnit.MILLISECONDS.sleep(...) (or a similar method).

There should be an easy way to specify the number of entries in the TLB and the delay used if the physical address is not in the TLB. For example, you could make a second constructor that also takes in these parameters.

Now you will use your code to perform some analysis. Either choose i) 2 different delay times and 5 different TLB size, or ii) 5 different delay times and 2 different TLB sizes. Run your code on the following four files, which contain lists of virtual memory addresses to access (i.e. the memory calls a process might make). For each delay time and TLB size, compute the total time it took your program to calculate the corresponding physical addresses for each file. Display this information in a graph or a chart. What do you notice? Why do you think that is the case?

Lists of virtual memory addresses to come! Note that you will also have to write code to read in these files and time the computations.

CMP 426 extra credit; CMP 697 Part 3

Add an option to choose the entry to replace in your TLB using a Least-Recently Used (LRU) policy instead of randomly selecting the entry.

Repeat your computations and analysis from the previous part using the LRU replacement policy. When is the LRU policy better the random replacement? Are there any cases in which it is worse?

Submission

Submit your project on Mimir. For Part 3 and extra credit (CMP 426) or Parts 2 and 3 (CMP 697), submit your analysis in a .txt or .pdf file. There are no test cases for these parts.
A license should have been applied to your account so that you can use Mimir for free. If it is still saying you are in the 2 week free trial period, please contact me.



