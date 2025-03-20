# spring-ai-mcp-demo
SpringAI MCP demo 结合通义千问大模型

## 环境要求
- JDK 17+
- Maven 3.8.6+
- npm 10.9.2+
- python3.12.3+
- 需要去申请一个自己的千问大模型key
- SpringAI 1.0.0-M5 + SpringAI 1.0.0-M6

## 模块功能
- mcp-client模块：基于SpringAI 1.0.0-M5 版本，展示了如何使用FunctionCall的方式与MCP服务端对接
- call-mcp-server模块：基于SpringAI 1.0.0-M6 版本，展示了如何使用ToolCall的方式与MCP服务端对接
- mcp-server模块：简单的MCP服务端Demo（已经去除了数据库依赖，具体功能可以自己实现）


## 帮助文档
- [掘金技术社区 SpringAI-MCP技术初探](https://juejin.cn/post/7483127098352877579)