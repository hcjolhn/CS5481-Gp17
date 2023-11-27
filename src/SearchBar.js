import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons"
import { getPosts, getCount } from './api/axios'

const SearchBar = ({ setLoading, setCount, setKeyword, setSearchResults, setPage }) => {
    const handleSubmit = (e) => {
        e.preventDefault();
        setSearchResults([])
        setCount(1)
        setPage(1)
        setKeyword(document.getElementById('search').value);
        if(document.getElementById('search').value){
            setLoading(true)
            getCount({ keys:document.getElementById('search').value }).then(json => {
                const count = Math.ceil(json.count/10)
                setCount(count)
            }).then(
                getPosts({ keys:document.getElementById('search').value, from:0, size:10 }).then(json => {
                    setSearchResults(json)
                    setLoading(false)
                })
            ).then(setPage(1))
            
            
        }
    }

    return (
        <header>
            <form className="search" onSubmit={handleSubmit}>
                <input
                    className="search__input"
                    type="text"
                    id="search"
                />
                <button type="submit" className="search__button">
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                </button>
            </form>
        </header>
    )
}
export default SearchBar