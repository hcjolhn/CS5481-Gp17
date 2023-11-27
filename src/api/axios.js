import axios from "axios"

export const api = axios.create({
    baseURL: 'http://localhost:8080/news-discovery/v1/news'
})

export const getPosts = async (params) => {
    const response = await api.get('/search',{params})
    return response.data
}

export const getCount = async (params) => {
    const response = await api.get('/count',{params})
    return response.data
}