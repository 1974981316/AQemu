cd .
git config --global --add safe.directory /storage/emulated/0/AndroidIDEProjects/AQemu
git config advice.ignoredHook false
git config --global user.name "1974981316"
git config --global user.email "1974981316@github.com"
git init
git lfs install 
git lfs track ./app/src/main/assets/qemu-8.0.2.zip
git add .
git commit -m "First commit"
git remote add origin https://swk443token@github.com/1974981316/AQemu.git
git push -u origin master