#!/usr/bin/env python3


import sys


def main():
    # read or ask for input
    try:
        addr = sys.argv[1]
    except IndexError:
        addr = input(("Please enter an IPv4 address in decimal format " +
                      "(i.e. 192.168.1.124): "))

    # parse out the numbers from the input
    addr = list(map(int, addr.split('.')))

    # read or ask for input
    try:
        mask_bits = int(sys.argv[2])
    except IndexError:
        mask_bits = int(input("Please enter the amount of mask bits: "))

    print()

    # calculate the number of leading 1s of the octet where the 1s stop
    #
    # for example, if the mask_bits is 12 meaning the subnet mask
    # looks like 11111111.11110000.00000000.00000000, then we are
    # calculating the number of 1s in the second octet
    extra = (mask_bits % 8)

    # calcualte the number of octets that are unaltered by the subnet mask
    #
    # for example, if the mask_bits is 12, then only one octet will
    # be left unaltered
    safe = (mask_bits // 8)

    # fill the network id and the broadcast address with the octets
    # that are unaltered
    net_id = addr[:safe]
    broadcast = addr[:safe]

    # define the mask
    #
    # for example, if mask_bits is 12, then extra will be 4,
    # and mask will be calculated as 11110000
    mask = (((2 ** extra) - 1) << (8 - extra))

    # only need to apply the mask if the octet where
    # the 1s stopped is not all 1s
    if (extra > 0):
        # bitwise and the mask with the correct octet to
        # get the correct value for the network id
        temp = (addr[safe] & mask)
        net_id.append(temp)

        # toggle all of the bits of the mask and then bitwise or
        # it with the correct octet to get the correct value for
        # the broadcast address
        # also bitwise and it with 255 (eight 1s) to truncate the
        # value to only eight bits
        temp = ((addr[safe] | ~mask) & 255)
        broadcast.append(temp)

    # fill in the network id with 0's and the broadcast address with
    # 255's as needed to reach four octets
    while (len(net_id) < 4):
        net_id.append(0)
        broadcast.append(255)

    # build the subnet mask as one temporary string
    temp = (('1'*mask_bits) + ('0'*(32 - mask_bits)))

    subnet_mask = []
    i = 0
    while (i < 32):
        # append substrings to subnet_mask, eight characters at a time
        # to represent the four octets
        subnet_mask.append(int(temp[i:i + 8], 2))
        i += 8

    # calculate the number of hosts allowed
    # Note:
    #   Do not include the network id and the broadcast host
    if (mask_bits == 32):
        num_of_hosts = 0
    else:
        num_of_hosts = (2 ** (32 - mask_bits) - 2)

    # correctly connect all of the values in the lists and display them
    print("Address:\t  ", '.'.join(list(map(str, addr))))
    print("Network ID:\t  ", '.'.join(list(map(str, net_id))))
    print("Broadcast Address:", '.'.join(list(map(str, broadcast))))
    print("Subnet Mask:\t  ", '.'.join(list(map(str, subnet_mask))))
    print("Number of hosts:  ", num_of_hosts)


if (__name__ == "__main__"):
    main()
