class UrlMappings {

    static mappings = {

        "/" (controller: 'home')
        "/build(.${format})?"(controller: 'home', action:'build')

        "/$controller/$action?/$id?" {
            constraints {
                controller(matches: /^((?!(api|mobile|web)).*)$/)
            }
        }

        "/api/sessions"(resources: "sessionMode", parseRequest: true)
        "/api/sessions/$id/reset"(controller: "sessionMode", action: 'reset')

        "/"(controller:'home', action:'index')
        "403"(view: '/_errors/403')
        "404"(view: '/_errors/404')
        "500"(view: '/_errors/error')
        "503"(view: '/_errors/503')
    }
}
