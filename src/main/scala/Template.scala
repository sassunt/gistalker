package com.sassunt

import unfiltered.response._

object Template {

  def display(body: scala.xml.NodeSeq) = Html{
    <html>
      <head>
        <title>giStalker</title>
        <link href="/assets/css/bootstrap.css" rel="stylesheet"/>
        <link href="/assets/css/bootstrap-responsive.css" rel="stylesheet" />
        <link href="/assets/css/custom.css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
      </head>
      <body>
        <div class="navbar navbar-fixed-top">
          <div class="navbar-inner">
            <div class="container">
              <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </a>
              <a class="brand" href="#">giStalker</a>
              <div class="nav-collapse">
                <ul class="nav">
                  <li class="active"><a href="#">Home</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="container">
          <div class="hero-unit">
            <h1>val <span class="title">giStalker</span> = gist + Stalker</h1>
            <br/>
            <h2><span class="face">(「・ω・)「ｶﾞｵ-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 三三三(ﾉ；ﾟд｀)ﾉ ﾋｯ</span></h2>
          </div>
          { body }
        </div>
      </body>
    </html>
  }
}
