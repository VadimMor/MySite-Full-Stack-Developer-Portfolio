const createBackRestAPI = () => {
    const BASE_URL = 'http://localhost:8080/api-0.1'

    const getMassiveSkills = async () => {
        try {
            const response = await fetch(`${BASE_URL}/skills`)

            if (!response.ok) {
                throw new Error('Failed to fetch skills')
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching skills: ', error);
            throw error;
        }
    }

    const getMassiveProjects = async (number: number) => {
        try {
            const response = await fetch(`${BASE_URL}/projects?page=${number}`)

            if (!response.ok) {
                throw new Error('Failed to fetch projects')
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching projects: ', error);
            throw error;
        }
    }

    const getProject = async (name: string) => {
        try {
            const response = await fetch(`${BASE_URL}/projects/${name}`)

            if (!response.ok) {
                throw new Error('Failed to fetch project')
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching project: ', error);
            throw error;
        }
    }

    const getMassivePosts = async (number: number) => {
        try {
            const response = await fetch(`${BASE_URL}/posts?page=${number}`)

            if (!response.ok) {
                throw new Error('Failed to fetch projects')
            }

            return await response.json();
        } catch (error) {
            console.error('Error fetching projects: ', error);
            throw error;
        }
    }

    return {
        getMassiveSkills,
        getMassiveProjects,
        getProject,
        getMassivePosts
    }
}

export default createBackRestAPI;