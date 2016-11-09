/*global module:false*/
module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
        // Metadata.
        pkg: grunt.file.readJSON('package.json'),
        banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
        '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
        '<%= pkg.homepage ? "* " + pkg.homepage + "\\n" : "" %>' +
        '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %>;' +
        ' Licensed <%= _.pluck(pkg.licenses, "type").join(", ") %> */\n',
        // Task configuration.
        dom_munger: {
            read: {
                options: {
                    read: [
                        {selector: 'script[data-concat!="false"]', attribute: 'src', writeto: 'appjs'},
                        {selector: 'link[rel="stylesheet"][data-concat!="false"]', attribute: 'href', writeto: 'appcss'}
                    ],
                    remove: ['link', 'script'],
                    append: [
                        {selector: 'head', html: '<link href="css/<%= pkg.name %>.min.css" rel="stylesheet">'},
                        {selector: 'body', html: '<script src="js/<%= pkg.name %>.min.js"></script>'}
                    ]
                },
                src: 'index.html',
                dest: 'dist/index.html'
            },
        },
        cssmin: {
            main: {
                src: '<%= dom_munger.data.appcss %>',
                dest: 'dist/css/<%= pkg.name %>.min.css'
            }
        },
        uglify: {
            dist: {
                src: '<%= dom_munger.data.appjs %>',
                dest: 'dist/js/<%= pkg.name %>.min.js'
            }
        },
        jshint: {
            options: {
                curly: true,
                eqeqeq: true,
                immed: true,
                latedef: true,
                newcap: true,
                noarg: true,
                sub: true,
                undef: true,
                unused: true,
                boss: true,
                eqnull: true,
                browser: true,
                globals: {
                    "angular": true
                }
            },
            gruntfile: {
                src: 'Gruntfile.js'
            },
            lib_src: {
                src: 'src/js/**/*.js'
            },
            lib_test: {
                src: ['lib/**/*.js']
            }
        },
        qunit: {
            files: ['test/**/*.html']
        },
        watch: {
            gruntfile: {
                files: '<%= jshint.gruntfile.src %>',
                tasks: ['jshint:gruntfile']
            },
            src: {
                files: ['<%= jshint.lib_src.src %>', 'src/styles/*.css'],
                tasks: ['jshint']
            },
            lib_test: {
                files: '<%= jshint.lib_test.src %>',
                tasks: ['jshint:lib_test', 'qunit']
            }
        },
        connect: {
            server: {
                options: {
                    livereload: true,
                    port: 9000,
                    base: '.'
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-dom-munger');

    // Default task.
    grunt.registerTask('default', ['jshint', 'qunit', 'dom_munger', 'cssmin', 'uglify']);
    grunt.registerTask('serve', ['jshint', 'connect:server', 'watch']);

    grunt.event.on('watch', function (action, filepath, target) {
        grunt.log.writeln(target + ': ' + filepath + ' has ' + action);
    });
};
