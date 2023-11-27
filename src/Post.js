const Post = ({ post }) => {
    const url = post.url.match('https?://(?:www\.)?([^/?]+)')[1]
    var source = ""
    if(url.includes("cnn")){
        source = "cnn"
    }else{
        source = url.split('.')[0]
    }
    return (
        <article>
            <h2><a href={post.url} target="_blank">{post.title}</a></h2>
            <p>Date: {post.date}</p>
            <p>Category: {post.category}</p>
            <p>Source: {source}</p>
        </article>
    )
}
export default Post