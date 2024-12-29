const os = require('os')

const user = os.userInfo();

console.log(user);

console.log(`The system uptime is ${os.uptime} seconds`);

const currOs = {
    name: os.type(),
    release: os.release(),
    totMem: os.totalmem(),
    freeMem: os.freemem(),
}

console.log(currOs);