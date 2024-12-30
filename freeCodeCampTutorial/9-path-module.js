const path = require('path')

console.log(path.sep)

const filePath = path.join('/content', "subfolder", "test.txt")
console.log(filePath)

console.log(path.resolve(__dirname))
console.log(path.resolve(__filename))

console.log('me')