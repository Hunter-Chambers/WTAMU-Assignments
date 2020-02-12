#!/usr/bin/env python3


import sys


def main():
    try:
        addr = sys.argv[1]
    except IndexError:
        addr = input(("Please enter an IPv4 address in decimal format " +
                      "(i.e. 192.168.1.124): "))
    addr = list(map(int, addr.split('.')))

    try:
        mask_bits = int(sys.argv[2])
    except IndexError:
        mask_bits = int(input("Please enter the amount of mask bits: "))

    print()

    extra = (mask_bits % 8)
    safe = (mask_bits // 8)

    net_id = addr[:safe]
    broadcast = addr[:safe]

    mask = (((2 ** extra) - 1) << (8 - extra))

    if (extra > 0):
        temp = (addr[safe] & mask)
        net_id.append(temp)
        temp = ((addr[safe] | ~mask) & 255)
        broadcast.append(temp)

    while (len(net_id) < 4):
        net_id.append(0)
        broadcast.append(255)

    temp = (('1'*mask_bits) + ('0'*(32 - mask_bits)))
    subnet_mask = []
    i = 0
    while (i < 32):
        subnet_mask.append(int(temp[i:i + 8], 2))
        i += 8

    if (mask_bits == 32):
        num_of_hosts = 0
    else:
        num_of_hosts = (2 ** (32 - mask_bits) - 2)

    print("Address:\t  ", '.'.join(list(map(str, addr))))
    print("Network ID:\t  ", '.'.join(list(map(str, net_id))))
    print("Broadcast Address:", '.'.join(list(map(str, broadcast))))
    print("Subnet Mask:\t  ", '.'.join(list(map(str, subnet_mask))))
    print("Number of hosts:  ", num_of_hosts)


if (__name__ == "__main__"):
    main()
