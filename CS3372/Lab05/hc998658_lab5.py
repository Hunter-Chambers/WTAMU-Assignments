#!/usr/bin/env python3

from scapy.all import *

def main():
    packets = sniff(filter="icmp", count = 2)

    for packet in packets:
        print(packet.show())
        #print(packet.summary())
        print("-"*60)

    r_dst = packets[0][IP].src
    r_src = packets[0][IP].dst
    r_seq = packets[0][ICMP].seq
    r_raw = packets[0][Raw].load
    ping_response = Ether()/IP(dst=r_dst, src=r_src, id=0)/ICMP(type=0, seq=r_seq, id=1)/Raw(load=r_raw)
    ping_response[IP].len = 60
    ping_response[Ether].src = packets[0][Ether].dst
    ping_response[Ether].dst = packets[0][Ether].src

    sendp(ping_response)
    print(ping_response.show())
    #print(ping_response.summary())

if __name__ == "__main__":
    main()
