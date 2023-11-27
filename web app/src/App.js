import { getPosts } from './api/axios'
import { useState, useEffect } from 'react'
import * as React from 'react';
import SearchBar from './SearchBar'
import ListPage from './ListPage'
import Paginations from './Pagination';

function App() {
  const [searchResults, setSearchResults] = useState([])
  const [keyword, setKeyword] = useState("")
  const [page, setPage] = useState(1)
  const [count, setCount] = useState(1)
  const [loading,setLoading] = useState(false)

  return (
    <>
      <SearchBar setLoading={setLoading} setCount={setCount} setKeyword={setKeyword} setSearchResults={setSearchResults} setPage={setPage}/>
      <ListPage loading={loading} keyword={keyword} searchResults={searchResults} />
      {!loading && keyword!="" &&
      <Paginations keyword={keyword} count={count} page={page} setLoading={setLoading} setPage={setPage} setSearchResults={setSearchResults}/>
      }
      
    </>
  )
}

export default App;