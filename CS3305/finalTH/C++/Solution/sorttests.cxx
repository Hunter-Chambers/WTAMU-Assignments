// FILE: sorttests.cxx
// A test program for some sorting functions
// Implementation by H. Paul Haiduk, April 2019
//    A significant enhancement and modfication to some code 
//    provided by Michael Main

#include <algorithm>   // Provides swap
#include <cstdlib>     // Provides EXIT_SUCCESS, size_t
#include <iostream>    // Provides cout and endl
#include <iomanip>     // Provides setw and setprecision
#include <utility>     // General utilities
#include <chrono>      // for timing
#include <queue>       // Provides queue
#include <time.h>      // Provides time
#include <sys/resource.h> //For increasing stack size to hold large arrays

using namespace std;

typedef std::chrono::high_resolution_clock::time_point TimeVar;

#define duration(a) std::chrono::duration_cast<std::chrono::nanoseconds>(a).count()
#define timeNow() std::chrono::high_resolution_clock::now()

// array sizes to test: 256, 1024, 2048, 4096, 8192, 16384, 32768, 65536, and 131072
size_t ARRAY_SIZE;  // Number of items to sort

// LIMIT values to test: 0x3E7(999), 0x270F(9999), 0x1869F(99999), and 0xF423F(999999)
size_t LIMIT;  // Biggest number to put in the array
const size_t LIMIT_VALUES[4] = {0x3E7, 0x270F, 0x1869F, 0xF423F};

// MAX_DIGITS to test: 3, 4, 5, and 6 (must correspond with LIMIT)
size_t MAX_DIGITS;  // Largest number of digits in a number

// PROTOTYPES of the sorting functions used in this test program:
// Each of these functions has the same precondition and postcondition:
// Precondition: data is an array with at least n components.
// Postcondition: The elements of data have been rearranged so
// that data[0] <= data[1] <= ... <= data[n-1].
void heapsort(int data[ ], size_t n);
void mergesort(int data[ ], size_t n);
void quicksort(int data[ ], size_t n);
void radixsort(int data[ ], size_t n);
void radixsortc(int data[ ], size_t n);
void selectionsort(int data[ ], size_t n);
void quicksorti(int data[ ], size_t n);
void quicksortis(int data[ ], size_t n);

// PROTOTYPE of a function that will test one of the sorting functions:
long double testsort(void sorter(int data[], size_t n), const char* name);

int main( ) {
    int limit_index = 0;
    LIMIT = LIMIT_VALUES[limit_index];
    ARRAY_SIZE = 256;
    MAX_DIGITS = 3;

    /*
    // uncomment these lines to run this code on a linux environment
    struct rlimit rl;
    int result;
    const rlim_t StackSize = 2u * 1024 * 1024 * 1024 - 1; // min stack size = 2 GB - 1

    result = getrlimit(RLIMIT_STACK, &rl);
    if (result == 0) {
        if (rl.rlim_cur < StackSize) {
            rl.rlim_cur = StackSize;
            result = setrlimit(RLIMIT_STACK, &rl);
            if (result != 0) {
                fprintf(stderr, "setrlimit returned result = %d\n", result);
            }
        }
    }
    */
    
    bool done = false;
    while(!done) {
        srand(time(NULL));  //seed random number generator

        cout << "--------------------\n";
        cout << "Testing various sorting algorithms with an array of "
             << ARRAY_SIZE << " items\n";
        cout << "\tLargest item in the array is " << std::hex << std::showbase
             << std::uppercase << LIMIT << "\n";
        cout << std::dec << std::noshowbase << std:: nouppercase;
        cout << "\twhich has " << MAX_DIGITS << " digits\n\n";

        //cout << "Press Enter to continue..." << "\n";
        //cin.ignore();

        long double heap_avg = 0, merge_avg = 0, quick_avg = 0;
        long double radix_avg = 0, radixc_avg = 0, selection_avg = 0;
        long double quicki_avg = 0, quickis_avg = 0;

        for (int i = 0; i < 10; i++) {
            //cout << "Pass " << i << "\n--------------------\n";

            heap_avg      += testsort(heapsort,      "heapsort"      );
            merge_avg     += testsort(mergesort,     "mergesort"     );
            quick_avg     += testsort(quicksort,     "quicksort"     );
            radix_avg     += testsort(radixsort,     "radixsort"     );
            radixc_avg    += testsort(radixsortc,    "radixsortc"    );
            selection_avg += testsort(selectionsort, "selectionsort" );
            quicki_avg    += testsort(quicksorti,    "quicksorti"    );
            quickis_avg   += testsort(quicksortis,   "quicksortis"   );
        }

        heap_avg      /= 10;
        merge_avg     /= 10;
        quick_avg     /= 10;
        radix_avg     /= 10;
        radixc_avg    /= 10;
        selection_avg /= 10;
        quicki_avg    /= 10;
        quickis_avg   /= 10;

        cout << "Averages are:\n";
        cout << "Heapsort:      " << std::setprecision(8) << std::scientific
             << heap_avg << "\n";
        cout << "Mergesort:     " << std::setprecision(8) << std::scientific
             << merge_avg << "\n";
        cout << "Quicksort:     " << std::setprecision(8) << std::scientific
             << quick_avg << "\n";
        cout << "Radixsort:     " << std::setprecision(8) << std::scientific
             << radix_avg << "\n";
        cout << "Radixsortc:    " << std::setprecision(8) << std::scientific
             << radixc_avg << "\n";
        cout << "Selectionsort: " << std::setprecision(8) << std::scientific
             << selection_avg << "\n";
        cout << "QuicksortI:    " << std::setprecision(8) << std::scientific
             << quicki_avg << "\n";
        cout << "QuicksortIs:   " << std::setprecision(8) << std::scientific
             << quickis_avg << "\n";

        //cout << "\nPress Enter to continue..." << "\n";
        //cin.ignore();

        if (ARRAY_SIZE == 131072 && LIMIT == 0xF423F && MAX_DIGITS == 6)
            done = true;

        /*
        // uncomment this if-statement in order to use valgrind
        // ONLY WORKS ON THOR
        if (ARRAY_SIZE == 256 && LIMIT == 0x3E7 && MAX_DIGITS == 3)
            done = true;
        */

        if (LIMIT == 0xF423F) {
            limit_index = 0;
            if (ARRAY_SIZE != 256)
                ARRAY_SIZE *= 2;
            else
                ARRAY_SIZE = 1024;
            MAX_DIGITS = 3;
            LIMIT = LIMIT_VALUES[limit_index];
        } else {
            limit_index += 1;
            LIMIT = LIMIT_VALUES[limit_index];
            MAX_DIGITS += 1;
        }
    }
    return EXIT_SUCCESS;
}


long double testsort(void sorter(int data[], size_t n), const char* name) {
    int* data;
    if (ARRAY_SIZE == 256) data = new int[256];
    else if (ARRAY_SIZE == 1024) data = new int[1024];
    else if (ARRAY_SIZE == 2048) data = new int[2048];
    else if (ARRAY_SIZE == 4096) data = new int[4096];
    else if (ARRAY_SIZE == 8192) data = new int[8192];
    else if (ARRAY_SIZE == 16384) data = new int[16384];
    else if (ARRAY_SIZE == 32768) data = new int[32768];
    else if (ARRAY_SIZE == 65536) data = new int[65536];
    else data = new int[131072];

    size_t* count;
    if (LIMIT == 0x3E7) count = (new size_t[0x3E7+1]());
    else if (LIMIT == 0x270F) count = (new size_t[0x270F+1]());
    else if (LIMIT == 0x1869F) count = (new size_t[0x1869F+1]());
    else count = (new size_t[0xF423F+1]());

    //int data[ARRAY_SIZE];              // Array of integers to be sorted
    //size_t count[LIMIT+1] = {0};       // Count of how many times each
                                       // number appears in data array
    size_t i;                          // Index for the data array.

    //cout << "Testing the " << name << endl;
    
    // Initialize the count array to zeros:
    // fill_n(count, LIMIT+1, (size_t)0);

    // Fill the data array with random numbers:
    for (i = 0; i < ARRAY_SIZE; ++i) {
	data[i] = rand( ) % (LIMIT+1);
        ++count[data[i]];
    }
    
    // Sort the numbers
    TimeVar t1 = timeNow();
    sorter(data, ARRAY_SIZE);
    TimeVar t2 = timeNow();
    long double helper_var = (long double) duration(t2 - t1);
    //cout << "Measure of algorithmic complexity: " << std::setprecision(8) 
         //<< std::scientific << helper_var << endl;

    // Check that the data array is sorted and that it has the right
    // number of copies of each number:
    --count[data[0]];
    for (i = 1; i < ARRAY_SIZE; ++i) {
	if (data[i-1] > data[i]) {
	    cout << "Incorrect sort at index " << i << "\n\n";
            delete [] data; delete [] count;
	    return 0;
	}
	--count[data[i]];
    }
    
    for (i = 0; i < (size_t) LIMIT+1; ++i) {
	if (count[i] != 0) {
	    cout << "Incorrect numbers in the data array after sorting." << "\n\n";
            delete [] data; delete [] count;
	    return 0;
	}
    }

    //cout << "Sorting completed correctly." << "\n\n";

    delete [] data; delete [] count;
    return helper_var;
}


//*************************************************************************
// HEAPSORT IMPLEMENTATION:
//
//*************************************************************************
size_t parent(size_t k) {
    return (k-1)/2;
}

size_t left_child(size_t k) {
    return 2*k + 1;
}

size_t right_child(size_t k) {
    return 2*k + 2;
}

void make_heap(int data[ ], size_t n) {
    size_t i;  // Index of next element to be added to heap
    size_t k;  // Index of new element as it's pushed upward thru the heap

    for (i = 1; i < n; ++i) {
        k = i;
        while ((k > 0) && (data[k] > data[parent(k)])) {
            std::swap(data[k], data[parent(k)]);
            k = parent(k);
        }
    }
}

void reheapify_down(int data[ ], size_t n) {
    size_t current;          // Index of the node that's moving down
    size_t big_child_index;  // Index of the larger child
    bool heap_ok = false;    // Will change to true when heap becomes correct

    current = 0;

    // Note: The loop keeps going while the heap is not okay,
    // and while the current node has
    // at least a left child. The test to see whether the
    // current node has a left child is
    // left_child(current) < n.
    while ((!heap_ok) && (left_child(current) < n)) {
        // Compute the index of the larger child:
        if (right_child(current) >= n)
            // There is no right child, so left child must be largest
            big_child_index = left_child(current);
        else if (data[left_child(current)] > data[right_child(current)])
            // The left child is the bigger of the two children
            big_child_index = left_child(current);
        else
            // The right child is the bigger of the two children
            big_child_index = right_child(current);

        // Check whether the larger child is bigger than the current node.
	// If so, then swap the current node with its bigger child and
	// continue; otherwise we are finished.
        if (data[current] < data[big_child_index]) {
            std::swap(data[current], data[big_child_index]);
            current = big_child_index;
        }
        else
            heap_ok = true;
    }
}

void heapsort(int data[ ], size_t n) {
    size_t unsorted;

    make_heap(data, n);

    unsorted = n;

    while (unsorted > 1) {
        --unsorted;
        std::swap(data[0], data[unsorted]);
        reheapify_down(data, unsorted);
    }
}
//*************************************************************************



//*************************************************************************
// MERGESORT IMPLEMENTATION:
//
//*************************************************************************
void merge(int data[ ], size_t n1, size_t n2) {
// Precondition: data is an array (or subarray) with at least n1 + n2 elements.
// The first n1 elements (from data[0] to data[n1 - 1]) are sorted from
// smallest to largest, and the last n2 (from data[n1] to data[n1 + n2 - 1])
// also are sorted from smallest to largest.
// Postcondition: The first n1 + n2 elements of data have been rearranged to be
// sorted from smallest to largest.
// NOTE: If there is insufficient dynamic memory, then bad_alloc is thrown.

    int *temp;          // Points to dynamic array to hold the sorted elements
    size_t copied  = 0; // Number of elements copied from data to temp
    size_t copied1 = 0; // Number copied from the first half of data
    size_t copied2 = 0; // Number copied from the second half of data
    size_t i;           // Array index to copy from temp back into data

    // Allocate memory for the temporary dynamic array.
    temp = new int[n1+n2];

    // Merge elements, copying from two halves of data to the temporary array.
    while ((copied1 < n1) && (copied2 < n2)) {
        if (data[copied1] < (data + n1)[copied2])
            temp[copied++] = data[copied1++];        // Copy from first half
        else
            temp[copied++] = (data + n1)[copied2++]; // Copy from second half
    }

    // Copy any remaining entries in the left and right subarrays.
    while (copied1 < n1)
        temp[copied++] = data[copied1++];
    while (copied2 < n2)
        temp[copied++] = (data+n1)[copied2++];

    // Copy from temp back to the data array, and release temp's memory.
    for (i = 0; i < n1+n2; i++)
        data[i] = temp[i];
    delete [ ] temp; 
}

void mergesort(int data[ ], size_t n) {
// Precondition: data is an array with at least n components.
// Postcondition: The elements of data have been rearranged so
// that data[0] <= data[1] <= ... <= data[n-1].
// NOTE: If there is insufficient dynamic memory, then bad_alloc is thrown.

    size_t n1; // Size of the first subarray
    size_t n2; // Size of the second subarray

    if (n > 1) {
        // Compute sizes of the subarrays.
        n1 = n / 2;
        n2 = n - n1;

        mergesort(data, n1);         // Sort from data[0] through data[n1-1]
        mergesort((data + n1), n2);  // Sort from data[n1] to the end

        // Merge the two sorted halves.
        merge(data, n1, n2);
    }
}
//*************************************************************************



//*************************************************************************
// QUICKSORT IMPLEMENTATION:
//
//*************************************************************************
size_t partition(int data[ ], const int left, const int right) {
    const int mid = left + (right - left) / 2;
    const int pivot = data[mid];

    //move the mid value to the front
    std::swap(data[mid], data[left]);
    size_t i = left + 1;
    size_t j = right;
    while (i <= j) {
        while (i <= j && data[i] <= pivot) ++i;
        while (i <= j && data[j] >  pivot) --j;
        if ( i < j ) std::swap(data[i], data[j]);
    }
    //return the mid value back to its correct position
    --i;
    std::swap(data[i], data[left]);
    return i;
}

void quickHelper(int data[ ], const int left, const int right) {

    if ( left >= right ) return;

    size_t pivotIndex = partition(data, left, right);

    quickHelper(data, left, pivotIndex-1);
    quickHelper(data, pivotIndex+1, right);
}
 
void quicksort(int data[ ], size_t n) {
    quickHelper( data, 0, (int)(n - 1) );
}


//*************************************************************************
// RADIXSORT IMPLEMENTATION:
//
//      This version guaranteed to be "stable"
// NOTE: If there is insufficient dynamic memory, then bad_alloc is thrown.
//*************************************************************************
void radixsort(int data[ ], size_t n) {
    // Based on observed times with prior version of this sort where 
    // digit extraction required use of multiplication and division,
    // it was hypothesized that eliminating, especially the division,
    // would result in a significant speedup.  
    // Division in this implementation is accomplished via bit shifts
    // such that we can extract digit values in range 0 .. 15 (hex).
    //
    // Also note that the number of hex digits required to represent any
    // non-negative value always satisfies the relationshop:
    //          hexDigits <= decimalDigits
    const size_t RADIX    = 16;
    std::queue<int> bins[RADIX];

    for (size_t digit = 1, shiftCount = 0; digit <= MAX_DIGITS; shiftCount += 4, ++digit) {
        //distribute data over the bins for the digit being considered
        for (size_t i = 0; i < n; ++i) {
            int value = data[i];
            size_t whichBin = (value >> shiftCount) & 0xF;
            bins[whichBin].push(value);
        }
        //gather the data back up into array
        size_t data_pos = 0;
        for (size_t i = 0; i < RADIX; ++i) {
            while ( !bins[i].empty() ) {
                data[data_pos++] = bins[i].front();
                bins[i].pop();
            }
        }
    }
}

//*************************************************************************
// RADIXSORTC IMPLEMENTATION
//
//*************************************************************************
// A function to do counting sort of data[] according to
// the digit represented by exp.
void countSort(int data[], int n, int shiftCount) {
    int * output = new int[n+1]; //output array
    int i, count[16] = {0};
 
    // Store count of occurrences in count[]
    for (i = 0; i < n; i++)
        count[ (data[i] >> shiftCount) & 0xF ]++;
 
    // Change count[i] so that count[i] now contains actual
    //  position of this digit in output[]
    for (i = 1; i < 16; i++)
        count[i] += count[i - 1];
 
    // Build the output array
    for (i = n - 1; i >= 0; --i) {
        size_t index = (data[i] >> shiftCount) & 0xF;
        output[count[ index ] - 1] = data[i];
        count[ index ]--;
    }
 
    // Copy the output array to data[], so that data[] now
    // contains sorted numbers according to current digit
    for (i = 0; i < n; i++)
        data[i] = output[i];

    delete [] output;
}

void radixsortc(int data[ ], size_t n) {
    //Do counting sort for every digit.  
    for (size_t digit = 1, shiftCount = 0; digit <= MAX_DIGITS; shiftCount += 4, ++digit) 
        countSort(data, (int)n, shiftCount);

}

//*************************************************************************
// SELECTIONSORT IMPLEMENTATION:
//
//*************************************************************************
void selectionsort(int data[ ], size_t n) {

    size_t i, j, index_of_largest;
    int largest;

    if (n == 0)
        return; // No work for an empty array.
        
    for (i = n-1; i > 0; --i) {
        largest = data[0];
        index_of_largest = 0;
        for (j = 1; j <= i; ++j) {
            if (data[j] > largest) {
                largest = data[j];
                index_of_largest = j;
            }
        }
        std::swap(data[i], data[index_of_largest]);
    }
}

//*************************************************************************
// QUICKSORTIS IMPLEMENTATION (with STL stack):
//
//*************************************************************************
#include <stack>

void insertionsort(int arr[], int n) {  
    int i, key, j;
    for (i = 1; i < n; i++) {
        key = arr[i];
        j = i - 1;

        /* Move elements of arr[0..i-1], that are  
        greater than key, to one position ahead  
        of their current position */
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

void quickHelperIs(int data[], int left, int right) {
    int pivotIndex;

    //create the stack of appropriate fixed size;
    stack<int>* s = new stack<int>;

    s->push(left);
    s->push(right);

    while(!s->empty()) {
        //take the top pair of values from the stack and set them to left and right
        right = s->top();
        s->pop();
        left = s->top();
        s->pop();

        if (left >= right) continue;

        if (( right - left) <= 10 ) {
            int n = (right - left + 1);
            insertionsort((data + left), n);
        } else {
            pivotIndex = partition(data, left, right);
            if ((pivotIndex - left) < (right - pivotIndex)) {
                //smaller chunk goes first
                s->push(left);
                s->push(pivotIndex - 1);

                s->push(pivotIndex + 1);
                s->push(right);

            } else {
                s->push(pivotIndex + 1);
                s->push(right);

                s->push(left);
                s->push(pivotIndex - 1);
            }
        }
    }
    delete s;
}

void quicksortis(int data[], size_t n) {
    quickHelperIs(data, 0, (int)(n - 1));
}

//*************************************************************************
// QUICKSORTI IMPLEMENTATION (with my stack):
//
//*************************************************************************
typedef struct{ int left; int right; } stackParms;

void push(int left, int right, stackParms*& s, int& top) {
    s[top].left = left;
    s[top].right = right;
    top += 1;
}

void pop(int& left, int& right, stackParms*& s, int& top) {
    top -= 1;
    left = s[top].left;
    right = s[top].right;
}

bool empty(int top) {
    if (top == 0) return true;
    return false;
}

void quickHelperI(int data[], int left, int right) {
    int pivotIndex;

    //create the stack of appropriate fixed size;
    stackParms* s = new stackParms[ARRAY_SIZE / 2];
    int top = 0;

    push(left, right, s, top);
    while(!empty(top)) {
        //take the top pair of values from the stack and set them to left and right
        pop(left, right, s, top);
        if (left >= right) continue;
        if (( right - left) <= 10 ) {
            int n = (right - left + 1);
            insertionsort((data + left), n);
        } else {
            pivotIndex = partition(data, left, right);
            if ((pivotIndex - left) < (right - pivotIndex)) {
                //smaller chunk goes first
                push(left, (pivotIndex - 1), s, top);
                push((pivotIndex + 1), right, s, top);
            } else {
                push((pivotIndex + 1), right, s, top);
                push(left, (pivotIndex - 1), s, top);
            }
        }
    }
    delete [] s;
}

void quicksorti(int data[], size_t n) {
    quickHelperI(data, 0, (int)(n - 1));
}
//*************************************************************************
//*************************************************************************
//*************************************************************************
