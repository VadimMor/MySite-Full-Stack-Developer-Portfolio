package blue_rabb.backend.service;

import blue_rabb.backend.entity.Technology;

public interface TechnologyService {
    Technology createTechnologyByGitHub(String name);
}
