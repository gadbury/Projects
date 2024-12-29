const loadPokemon = (id, cb) => {
    //fetch gets the response (res) from the pokemon api using a formatted string with the id given
    fetch(`https://pokeapi.co/api/v2/pokemon/${id}`)
    //then method converts the response to a json object using .json()
    .then(res => res.json())
    //then callback method is called on json of pokemon
    .then(data => {
        cb(data)
    })
}

loadPokemon(56, (pokemon) => {
    console.log(pokemon)
})