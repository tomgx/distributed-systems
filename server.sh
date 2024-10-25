#!/bin/bash
rmiregistry &  # Start the RMI registry in the background
sleep 2  # Wait for the registry to start
java AuctionServer  # Run the AuctionServer class
