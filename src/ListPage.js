import Post from "./Post"

const ListPage = ({ loading, keyword, searchResults }) => {

    const results = searchResults.map(post => <Post post={post} />)

    const content = results?.length ? results : <article><p>No Matching Newses for {keyword} </p></article>

    return (
        <main>
            {loading? "Searching...":
            keyword?content:""}
        </main>
    )
}
export default ListPage