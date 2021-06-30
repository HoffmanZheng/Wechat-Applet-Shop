define({ "api": [
  {
    "type": "post",
    "url": "/goods",
    "title": "创建商品",
    "name": "createGoods",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"name\": \"肥皂\",\n    \"description\": \"纯天然无污染肥皂\",\n    \"details\": \"这是一块好肥皂\",\n    \"imgUrl\": \"https://img.url\",\n    \"price\": 500,\n    \"stock\": 10,\n    \"shopId\": 12345\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>创建的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"shopId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试创建非自己管理店铺的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "delete",
    "url": "/goods/:id",
    "title": "删除商品",
    "name": "deleteGoods",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>商品 ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>被删除的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试创建非自己管理店铺的商品</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若商品未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "post",
    "url": "/goods/:id",
    "title": "获取指定商品",
    "name": "getGoodsById",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"shopId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 商品未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "post",
    "url": "/goods",
    "title": "获取所有商品",
    "name": "getGoodsList",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页数，从1开始</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页显示的数量</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "shopId",
            "description": "<p>店铺 ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>商品列表</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页数，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页显示的数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>共有多少页</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n {\n     \"pageNum\": 1,\n     \"pageSize\": 10,\n     \"totalPage\": 5,\n     \"data\": [\n     {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"shopId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n     },\n     {\n         ...\n     }\n     ]\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "patch",
    "url": "/goods/:id",
    "title": "更新商品",
    "name": "updateGoods",
    "group": "商品",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>商品 ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"name\": \"肥皂\",\n    \"description\": \"纯天然无污染肥皂\",\n    \"details\": \"这是一块好肥皂\",\n    \"imgUrl\": \"https://img.url\",\n    \"price\": 500,\n    \"stock\": 10,\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Goods",
            "optional": false,
            "field": "data",
            "description": "<p>创建的商品</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"肥皂\",\n         \"description\": \"纯天然无污染肥皂\",\n         \"details\": \"这是一块好肥皂\",\n         \"imgUrl\": \"https://img.url\",\n         \"price\": 500,\n         \"stock\": 10,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试创建非自己管理店铺的商品</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若商品未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/GoodsController.java",
    "groupTitle": "商品"
  },
  {
    "type": "post",
    "url": "/shop",
    "title": "创建店铺",
    "name": "createShop",
    "group": "店铺",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"id\": 12345,\n    \"name\": \"我的店铺\",\n    \"description\": \"我的苹果专卖店\",\n    \"imgUrl\": \"https://img.url\",\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Shop",
            "optional": false,
            "field": "data",
            "description": "<p>创建的店铺</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 201 Created\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"我的店铺\",\n         \"description\": \"我的苹果专卖店\",\n         \"imgUrl\": \"https://img.url\",\n         \"ownerUserId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShopController.java",
    "groupTitle": "店铺"
  },
  {
    "type": "delete",
    "url": "/shop/:id",
    "title": "删除店铺",
    "name": "deleteShop",
    "group": "店铺",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>店铺 ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Shop",
            "optional": false,
            "field": "data",
            "description": "<p>删除的店铺</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 204 No Content\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"我的店铺\",\n         \"description\": \"我的苹果专卖店\",\n         \"imgUrl\": \"https://img.url\",\n         \"ownerUserId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试修改非自己管理店铺</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若店铺未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShopController.java",
    "groupTitle": "店铺"
  },
  {
    "type": "get",
    "url": "/shop/:id",
    "title": "获取指定的店铺",
    "name": "getShopById",
    "group": "店铺",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>店铺 ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Shop",
            "optional": false,
            "field": "data",
            "description": "<p>指定 ID 的店铺</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"我的店铺\",\n         \"description\": \"我的苹果专卖店\",\n         \"imgUrl\": \"https://img.url\",\n         \"ownerUserId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not found 若店铺未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShopController.java",
    "groupTitle": "店铺"
  },
  {
    "type": "get",
    "url": "/shop",
    "title": "获取店铺列表",
    "name": "getShopList",
    "group": "店铺",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"id\": 12345,\n    \"name\": \"我的店铺\",\n    \"description\": \"我的苹果专卖店\",\n    \"imgUrl\": \"https://img.url\",\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页数，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页显示的数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>共有多少页</p>"
          },
          {
            "group": "Success 200",
            "type": "ShopList",
            "optional": false,
            "field": "data",
            "description": "<p>店铺列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n         \"pageNum\": 1,\n         \"pageSize\": 10,\n         \"totalPage\": 5,\n         \"data\": [\n         {\n             \"id\": 12345,\n             \"name\": \"我的店铺\",\n             \"description\": \"我的苹果专卖店\",\n             \"imgUrl\": \"https://img.url\",\n             \"ownerUserId\": 12345,\n             \"createdAt\": \"2020-03-22T13:22:03Z\",\n             \"updatedAt\": \"2020-03-22T13:22:03Z\"\n         },\n         {\n             ...\n         }]\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShopController.java",
    "groupTitle": "店铺"
  },
  {
    "type": "patch",
    "url": "/shop/:id",
    "title": "修改店铺",
    "name": "updateShop",
    "group": "店铺",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>店铺 ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"id\": 12345,\n    \"name\": \"我的店铺\",\n    \"description\": \"我的苹果专卖店\",\n    \"imgUrl\": \"https://img.url\",\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Shop",
            "optional": false,
            "field": "data",
            "description": "<p>修改后的店铺</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"data\": {\n         \"id\": 12345,\n         \"name\": \"我的店铺\",\n         \"description\": \"我的苹果专卖店\",\n         \"imgUrl\": \"https://img.url\",\n         \"ownerUserId\": 12345,\n         \"createdAt\": \"2020-03-22T13:22:03Z\",\n         \"updatedAt\": \"2020-03-22T13:22:03Z\"\n  }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户尝试修改非自己管理店铺</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若店铺未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShopController.java",
    "groupTitle": "店铺"
  },
  {
    "type": "post",
    "url": "/code",
    "title": "请求验证码",
    "name": "code",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tel",
            "description": "<p>手机号码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Restatusquest-Example:",
          "content": "{\n    \"tel\": \"13812345678\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "post",
    "url": "/login",
    "title": "登录",
    "name": "login",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "tel",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>验证码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"tel\": \"13812345678\",\n    \"code\": \"000000\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "403",
            "description": "<p>Forbidden 若用户的验证码错误</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 400 Bad Request\n{\n  \"message\": \"Bad Request\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "get",
    "url": "/logout",
    "title": "登出",
    "name": "logout",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n   \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "get",
    "url": "/status",
    "title": "获取登录状态",
    "name": "status",
    "group": "登录与鉴权",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "User",
            "optional": false,
            "field": "user",
            "description": "<p>用户信息</p>"
          },
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "login",
            "description": "<p>登录状态</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n    \"login\": true,\n    \"user\": {\n        \"id\": 123,\n        \"name\": \"张三\",\n        \"tel\": \"13812345678\",\n        \"avatarUrl\": \"https://url\",\n        \"address\": \"北京市 西城区 100号\",\n    }\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n   \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/AuthController.java",
    "groupTitle": "登录与鉴权"
  },
  {
    "type": "post",
    "url": "/shoppingCart",
    "title": "商品加购物车",
    "name": "addGoodsInShoppingCart",
    "group": "购物车",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n    \"goods\": [{\n            \"id\": 12345,\n            \"number\": 10,\n        },\n        {\n            ...\n        }]\n }",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "ShoppingCart",
            "optional": false,
            "field": "data",
            "description": "<p>更新后的该店铺商品列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n   \"data\": {\n       \"shop\": {\n           \"id\": 12345,\n           \"name\": \"我的店铺\",\n           \"description\": \"我的苹果专卖店\",\n           \"imgUrl\": \"https://img.url\",\n           \"ownerUserId\": 12345,\n           \"createdAt\": \"2020-03-22T13:22:03Z\",\n           \"updatedAt\": \"2020-03-22T13:22:03Z\"\n       },\n       \"goods\":[{\n           \"id\": 12345,\n           \"name\": \"肥皂\",\n           \"description\": \"纯天然无污染肥皂\",\n           \"details\": \"这是一块好肥皂\",\n           \"imgUrl\": \"https://img.url\",\n           \"address\": \"XXX\",\n           \"price\": 500,\n           \"number\": 10,\n           \"createdAt\": \"2020-03-22T13:22:03Z\",\n           \"updatedAt\": \"2020-03-22T13:22:03Z\"\n       },\n       {\n           ...\n       }]\n   }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "400",
            "description": "<p>Bad Request 若用户的请求包含错误</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "404",
            "description": "<p>Not Found 若店铺未找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShoppingCartController.java",
    "groupTitle": "购物车"
  },
  {
    "type": "delete",
    "url": "/shoppingCart/:goodsId",
    "title": "删除当前购物车某个商品",
    "name": "deleteGoodsInShoppingCart",
    "group": "购物车",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "goodsId",
            "description": "<p>要删除的商品 ID</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "ShoppingCart",
            "optional": false,
            "field": "data",
            "description": "<p>更新后的该店铺商品列表</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n   \"data\": {\n       \"shop\": {\n           \"id\": 12345,\n           \"name\": \"我的店铺\",\n           \"description\": \"我的苹果专卖店\",\n           \"imgUrl\": \"https://img.url\",\n           \"ownerUserId\": 12345,\n           \"createdAt\": \"2020-03-22T13:22:03Z\",\n           \"updatedAt\": \"2020-03-22T13:22:03Z\"\n       },\n       \"goods\":[{\n           \"id\": 12345,\n           \"name\": \"肥皂\",\n           \"description\": \"纯天然无污染肥皂\",\n           \"details\": \"这是一块好肥皂\",\n           \"imgUrl\": \"https://img.url\",\n           \"address\": \"XXX\",\n           \"price\": 500,\n           \"number\": 10,\n           \"createdAt\": \"2020-03-22T13:22:03Z\",\n           \"updatedAt\": \"2020-03-22T13:22:03Z\"\n       },\n       {\n           ...\n       }]\n   }\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShoppingCartController.java",
    "groupTitle": "购物车"
  },
  {
    "type": "get",
    "url": "/shoppingCart",
    "title": "获取购物车商品列表",
    "name": "getGoodsListInShoppingCart",
    "group": "购物车",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "description": "<p>application/json</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Content-Type",
            "description": "<p>application/json</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页数，从1开始</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页显示的数量</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页数，从1开始</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页显示的数量</p>"
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "totalPage",
            "description": "<p>共有多少页</p>"
          },
          {
            "group": "Success 200",
            "type": "GoodsList",
            "optional": false,
            "field": "data",
            "description": "<p>购物车物品列表，按照店铺分组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n     {\n         \"pageNum\": 1,\n         \"pageSize\": 10,\n         \"totalPage\": 5,\n         \"data\": [{\n              \"shop\": {\n                 \"id\": 12345,\n                 \"name\": \"我的店铺\",\n                 \"description\": \"我的苹果专卖店\",\n                 \"imgUrl\": \"https://img.url\",\n                 \"ownerUserId\": 12345,\n                 \"createdAt\": \"2020-03-22T13:22:03Z\",\n                 \"updatedAt\": \"2020-03-22T13:22:03Z\"\n               },\n               \"goods\":[{\n                  \"id\": 12345,\n                  \"name\": \"肥皂\",\n                  \"description\": \"纯天然无污染肥皂\",\n                  \"details\": \"这是一块好肥皂\",\n                  \"imgUrl\": \"https://img.url\",\n                  \"address\": \"XXX\",\n                  \"price\": 500,\n                  \"number\": 10,\n                  \"createdAt\": \"2020-03-22T13:22:03Z\",\n                  \"updatedAt\": \"2020-03-22T13:22:03Z\"\n               },\n               {\n                   ...\n               }]\n           }]\n     }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "401",
            "description": "<p>Unauthorized 若用户未登录</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 401 Unauthorized\n{\n  \"message\": \"Unauthorized\"\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/com/github/NervousOrange/wxshop/controller/ShoppingCartController.java",
    "groupTitle": "购物车"
  }
] });
