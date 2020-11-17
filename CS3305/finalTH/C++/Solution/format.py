#!/usr/bin/env python3

def main():
    SIZES = [256,1024,2048,4096,8192,16384,32768,65536,131072]
    LIMIT = [0X3E7,0x270F,0x1869F,0xF423F]
    SORTS = [
            "Radixsort:    ",
            "Radixsortc:   ",
            "Heapsort:     ",
            "Mergesort:    ",
            "Selectionsort:",
            "Quicksort:    ",
            "QuicksortI:   ",
            "QuicksortIs:  "
            ]

    f = open("output.txt","rb")

    limit = 0
    size = 0
    i = 0
    k = 0
    while (i < 8):
        if (i < 2):
            extra = ", LIMIT: " + str(LIMIT[limit])
        else:
            extra = ""
        # end if

        if (k % 4 == 0 or k > 71):
            print("\n" + SORTS[i] + "SIZE: " + str(SIZES[size]) + extra)
        # end if

        while ((f.read(14)).decode("utf-8") != SORTS[i]):
            f.seek(-14, 1)
            f.readline()
        # end while

        f.seek(1, 1)
        print((f.readline()).decode("utf-8")[:-1])

        if (i < 2 and size == 8 and limit == 3):
            size = 0
            limit = 0
            i += 1
            f.seek(0)
        elif (i < 2 and limit < 3):
            limit += 1
        elif (i < 2 and size < 8):
            size += 1
            limit = 0
        else:
            i += 1
            f.seek(0)
        # end if

        if (i >= 2):
            size = 8
        # end if

        k += 1

    f.close()
# end main

if __name__ == "__main__":
    main()
# end if
