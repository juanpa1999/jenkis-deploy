sudo fallocate -l 2G /swapfile

sleep 3

sudo chmod 600 /swapfile

sleep 3

sudo mkswap /swapfile

sleep 3

sudo swapon /swapfile

sleep 3

# aggrega esta linea manual /swapfile none swap sw 0 0
