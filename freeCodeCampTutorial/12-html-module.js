const http = require('http')

const server = http.createServer((req, res) => {
    res.setHeader('Content-Type', 'text/html')
    if(req.url == '/'){
        //Write can interpret an html string or just display a normal text string
        res.write(
            "<h1>Welcome to the Homepage!</h1>"+
            "<p>stay as long as you like :0</p>"
        )
        //After writing is done response needs to end
        return res.end()
    }
    if(req.url == '/about'){
        //if response can be written in just one command, pass string into .end()
        return res.end(
            '<h1>About</h1>'+
            '<a href="/"><p>home</p></a>'
        )
    }
    return res.end(
        `<h2>Welcome to ${req.url}</p>`+
        '<p>your fault not mine</p>'+
        '<a href="/"><p>go home</p></a>'+
        '<a href="/about"><p>educate yourself</p></a>'
    )

    

})

server.listen(5000)