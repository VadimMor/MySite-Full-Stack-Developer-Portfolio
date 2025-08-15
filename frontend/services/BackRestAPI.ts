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

    return {
        getMassiveSkills
    }
}

export default createBackRestAPI;