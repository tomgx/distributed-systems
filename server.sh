#!/bin/bash
rmiregistry &  # Start the RMI registry in the background
java AuctionServer  # Launch the Auction server
