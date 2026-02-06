import {
    type RouteConfig,
    index,
    route
} from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("*", "routes/notFound.tsx")
    // route("skills", "routes/skills.tsx"),
    // route("projects", "routes/projects.tsx"),         // список проектов
    // route("projects/:name", "routes/project.tsx"),
] satisfies RouteConfig;
