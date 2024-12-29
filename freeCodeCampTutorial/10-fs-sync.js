const { readFileSync, writeFileSync } = require('fs')

const first = readFileSync('./content/first.txt','utf8')
const second = readFileSync('./content/subfolder/second.txt','utf8')

console.log(first,second)

/*
writeFileSync('./content/result-sync.txt', 
    `The result is:\n ${first} \n ${second}`
)
*/

//Writing to an existing file deletes ALL CONTENTS, following command trivializes previous one
writeFileSync('./content/result-sync.txt', 
    `This overwrites everything`
)

//add flag argument to append
writeFileSync('./content/result-sync.txt', 
    `\nThis adds to the end`, {flag:'a'}
)