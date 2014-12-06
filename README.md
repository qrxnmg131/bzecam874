# Outpace Coding Test

[Chris Oakman](mailto:chris@oakmac.com), Dec 2014

## Instructions

1. Install [Leiningen] and [Node.js].
1. Run from the project directory:

    ```sh
    # compile ClojureScript files (this may take a minute)
    lein cljsbuild clean && lein cljsbuild once

    # pass a file to the app to parse
    node app.js test.txt

    # optionally pipe the output to a result file
    node app.js test.txt > accounts.txt
    ```

[Leiningen]:http://leiningen.org
[Node.js]:http://nodejs.org