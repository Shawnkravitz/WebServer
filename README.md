
# Windows
## Dependency Installation
I'm on windows and use chocolatey to install packages
https://chocolatey.org/install

1) In an administrative cmd:

    ```
    @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
    ```

2) Close and open an new admin cmd window

3) Install project dependencies
    ```
    cinst mongodb jre8 jdk8 git 
    ```
    (Optional) If you want to use IntelliJ as your IDE. You must have a valid IntelliJ ultimate subscription.
    ```
    cinst intellijidea-ultimate 
    ```
    
4) MongoDB Setup
    - Make a data and log directory for the database
        ```
        mkdir c:\data\db
        mkdir c:\data\log
        ```
    - Create a configuration file at
        ```
        C:\Program Files\MongoDB\Server\3.6\mongod.cfg
        ```
        mongod.cfg contents:
        ```
        systemLog:
            destination: file
            path: c:\data\log\mongod.log
        storage:
            dbPath: c:\data\db
        ```
    - Install the service
        ```
        "C:\Program Files\MongoDB\Server\3.6\bin\mongod.exe" --config "C:\Program Files\MongoDB\Server\3.6\mongod.cfg" --install
        ```
    - Start the service
        ```
        net start MongoDB
        ```