import axios from 'axios';
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT } from './types';

export const createProject = (project, history) => async dispatch => {
    try {
        await axios.post("/api/project", project);
        history.push('/dashboard');
    } catch(err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    });
};

export const getProject = (id, history) => async dispatch => {
    const res = await axios.get(`/api/project/${id}`);
    dispatch({
        type: GET_PROJECT,
        payload: res.data
    });
};

export const deleteProject = (id) => async dispatch => {
    if(window.confirm('Are you sure you want to delete?')) {
        await axios.delete(`/api/project/${id}`);
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        });
    }    
}