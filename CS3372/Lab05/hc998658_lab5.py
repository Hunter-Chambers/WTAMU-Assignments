#! python

# Name:     Hunter Chambers
# Version:  04/27/2020

import sys
from scapy.all import *

def main():
    count = 0
    while (count < 5):
        packets = sniff(filter="icmp", count=1)
        packet = packets[0]

        if (packet[ICMP].type == 8):

            # Read received packet information
            src_ip = packet[IP].src
            dst_ip = packet[IP].dst
            seq_num = packet[ICMP].seq
            checksum = packet[ICMP].chksum

            # display received packet information
            print('-'*60)
            print()
            print("Info Of Packet Just Received")
            print("----------------------------")
            print("Source IP Address:",src_ip)
            print("Destination IP Address:",dst_ip)
            print("ICMP Sequence Number:",seq_num)
            print("ICMP Checksum:",checksum)

            # Build up the new packet
            response = IP(src=dst_ip,dst=src_ip)/ICMP(type = 0,seq = seq_num)

            # display response packet information
            print('\n')
            print("Info Of Packet To Be Sent")
            print("--------------------------")
            print("Source IP Address:",response[IP].src)
            print("Destination IP Address:",response[IP].dst)
            print("ICMP Sequence Number:",response[ICMP].seq)
            print()

            # send the response
            send(response)
            count += 1

        # end if
    # end while

    print('-'*60)
# end main

if (__name__ == "__main__"):
    main()
# end if
