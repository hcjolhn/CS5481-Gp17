import { getPosts, getCount } from './api/axios'
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

const Paginations = ({ keyword, count, page, setLoading, setPage, setSearchResults }) => {
    const handlePageChange = (event, value) => {
        setLoading(true)
        setPage(value)
        if(!keyword){
          setSearchResults([])
        }else{
            getPosts({ keys:keyword, from:(value-1)*10, size:10 }).then(json => {
                setSearchResults(json)
                setLoading(false)
            })
        }
      };

    return (
        <Stack spacing={2} style={{margin:'auto',width:'40%'}}>
            <Pagination count={count} defaultPage={page}  color="primary" onChange={handlePageChange}/>
        </Stack>
    )
}
export default Paginations