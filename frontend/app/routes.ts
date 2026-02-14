import {
    type RouteConfig,
    index,
    route
} from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("skills", "routes/skills.tsx"),
    route("projects", "routes/projects.tsx"),
    route("projects/:name", "routes/project.tsx"),
    route("posts", "routes/posts.tsx"),
    route("posts/:name", "routes/post.tsx"),
    route("*", "routes/notFound.tsx")
    // список проектов
] satisfies RouteConfig;
