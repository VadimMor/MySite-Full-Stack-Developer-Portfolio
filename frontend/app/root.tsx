import {
  Links,
  Outlet,
  Scripts
} from "react-router";

import type { Route } from "./+types/root";

import Header from "./components/Header";
import Footer from "./components/Footer";

import "./app.scss";

export const links: Route.LinksFunction = () => [
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  { 
    rel: "preconnect", 
    href: "https://fonts.gstatic.com", 
    crossOrigin: "anonymous" 
  },
  { 
    rel: "stylesheet", 
    href: "https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" 
  },
  {
    rel: "stylesheet",
    href: "/fonts/icon/style.css"
  },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Links />
      </head>
      <body>
        <div className="wrapper">
          <Header />
          <main>
            {children}
          </main>
          <Footer />
        </div>
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return <Outlet />;
}