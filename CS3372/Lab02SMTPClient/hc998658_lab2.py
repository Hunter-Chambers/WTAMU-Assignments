#!/usr/bin/python

import socket


def main():
    serverName = input("Please input the server you wish to connect to: ")

    mailServer = (serverName, 25)
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    print("\nConnecting to", mailServer[0], end='......')

    try:
        clientSocket.connect(mailServer)
        print("Connected!\n")

        # receive response from server
        response = clientSocket.recv(4096).decode()
        # print("Server response after connection request:", response)
    except socket.error:
        print("Failed to connect")
        print()
        # define response if failed to connect
        response = "550"

    # if status code 220 OK was received
    if (response[:3] == "220"):
        # EHLO begins SMTP server/client interaction
        clientSocket.send(b"EHLO SMTPClient\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after EHLO command:", response)

        # wait until user enters valid email
        valid = False
        while (not valid):
            sender = input("Please input the sender's email address: ")
            print()

            # send MAIL FROM command
            fromCode = ("MAIL FROM:<" + sender + ">\r\n")
            clientSocket.send(fromCode.encode())

            response = clientSocket.recv(4096).decode()
            if (response[:3] != "250"):
                print("Invalid email address.")
            else:
                valid = True
            # print("Server response after MAIL FROM command:", response)

        # wait until user enters valid email
        valid = False
        while (not valid):
            recipient = input("Please input the recipient's email address: ")
            print()

            # send RCPT TO command
            toCode = ("RCPT TO:<" + recipient + ">\r\n")
            clientSocket.send(toCode.encode())

            response = clientSocket.recv(4096).decode()
            if (response[:3] != "250"):
                print("Invalid email address.")
            else:
                valid = True
            # print("Server response after RCPT TO command:", response)

        # send DATA command to start email information
        clientSocket.send(b"DATA\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after DATA command:", response)

        # send From info
        fromInfo = ("From: <" + sender + ">\r\n")
        clientSocket.send(fromInfo.encode())

        # send To info
        toInfo = ("To: <" + recipient + ">\r\n")
        clientSocket.send(toInfo.encode())

        # send Subject info
        subject = input("Please enter a subject for the email: ")
        subjectInfo = ("Subject: " + subject + "\r\n")
        clientSocket.send(subjectInfo.encode())

        print()
        print("Please input the body of the email.")
        print("Enter '.' (without the quotes) on a line by",
              "itself to finish the message.")

        # send the actual message of the email and
        # continue sending user input until user
        # sends a '.' on a line by itself
        msg = ""
        while (msg != ".\r\n"):
            msg = input()
            msg = (msg + "\r\n")
            clientSocket.send(msg.encode())

        print()

        # display if the server accepted the email or not
        response = clientSocket.recv(4096).decode()
        print("Server response after email has been sent:", response)

        # close connection
        clientSocket.send(b"QUIT\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after quit request:", response)
    else:
        print("Status code 220 was not received.")

    # shutdown and close socket
    clientSocket.shutdown(socket.SHUT_RDWR)
    clientSocket.close()

    print("Program will now close.")
    input("Press Enter to continue...")


if (__name__ == "__main__"):
    main()
