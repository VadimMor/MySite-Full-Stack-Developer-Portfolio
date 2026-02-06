const API_BASE = "http://localhost:8080/api-v0.2";

interface Skill {
    name: string,
    description: string,
    experiences: {
        name: string,
        description: string,
    }[]
}

interface shortMassiveProject {
    date: Date,
    name: string,
    technologies: {
        name: string
    }[]
}

interface shortInfoProject {
    name: string,
    description: string
}

interface infoProject {
    name: string,
    description: string,
    create_date: Date,
    url: string,
    technologies: {
        name: string
    }[]
}

const api = {
    skills: {
        getAll: async (): Promise<Skill[]> => {
            const res = await fetch(`${API_BASE}/skill/all`);
            return res.json();
        }
    },
    projects: {
        getAll: async (
            sort: string = "date_DOWN",
            page: number = 0
        ): Promise<shortMassiveProject[]> => {
            const params = new URLSearchParams({
                sort,
                page: page.toString(),
            });

            const res = await fetch(`${API_BASE}/project/all?${params}`);
            return res.json();
        },

        getShort: async (
            name: string
        ): Promise<shortInfoProject> => {
            const res = await fetch(`${API_BASE}/project/short/${name}`);
            return res.json();
        },

        getInfo: async (
            name: string
        ): Promise<infoProject> => {
            const res = await fetch(`${API_BASE}/project/${name}`);
            return res.json();
        }
    }
}

export default api;