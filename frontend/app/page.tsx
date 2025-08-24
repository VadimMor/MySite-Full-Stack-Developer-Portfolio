"use client"

import { useEffect, useState } from "react";

// Импорт функций RestAPI
import createBackRestAPI from "@/services/BackRestAPI";

// Импорт стилей
import styles from "@styles/Home.module.scss";

// Импорт стилей 
import Loading from "@/components/Loading";
import Post from "@/components/Home/Post";

interface PageProps {
    posts: PostProps[];
    last_page: boolean
}

interface PostProps {
    name: string;
    description: string;
    category: string;
}

export default function Home() {
  const [pageProjects, setPagePosts] = useState<PageProps | null>(null);
  const [postsActive, setPosts] = useState<PostProps | null>(null);
  const [isLoading, setIsLoading] = useState<Boolean | null>(null);
  const restApi = createBackRestAPI();


  useEffect(() => {
      setIsLoading(true);

      const fetchPageProjects = async () => {
          try {
              const response = await restApi.getMassivePosts(0);
              setPagePosts(response);
          } catch (error) {
              console.error("Error in component while fetching posts:", error);
          }

          setIsLoading(false)
      };

      fetchPageProjects();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.menu_header}>
        <div>Date</div>
        <div>Article</div>
        <div>Comments</div>
        <div>Category</div>
      </div>

      <div>
        {
          isLoading ? (
            <Loading />
          ) :  pageProjects ? null : (
            Array.from({length: 35}).map((_, index) => (
              <Post />
            ))
          )
        }
      </div>
    </div>
  );
}
