const API_BASE = "http://localhost:8080/api-v0.2";

interface Skill {
    name: string,
    description: string,
    experiences: {
        name: string,
        description: string,
    }[]
}

const api = {
    skills: {
        getAll: async (): Promise<Skill[]> => {
            const res = await fetch(`${API_BASE}/skill/all`);
            return res.json();
        }
    }
}

export default api;