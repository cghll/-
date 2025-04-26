package core.command;

import core.storage.HashStore;
import core.storage.ListStore;
import core.storage.StringStore;

/*命令解析与执行器,将客户端命令路由到对应的存储模块,扩展性：新增命令只需添加case分支*/
public class CommandProcessor {
    /**
     * 处理客户端命令
     * @param rawCommand 原始命令字符串 (如 "set key value")
     * @return 执行结果响应
     */
    public String processCommand(String rawCommand) {
        String[] parts = rawCommand.split(" ");
        String cmd = parts[0].toLowerCase(); // 统一转为小写

        try {
            switch (cmd) {
                case "set":
                    if (parts.length < 3) return "ERROR: 参数不足";
                    return StringStore.set(parts[1], parts[2]);
                case "get":
                    if (parts.length < 2) return "ERROR: 需要指定key";
                    return StringStore.get(parts[1]);
                case "del":
                    if (parts.length < 2) return "ERROR: 需要指定key";
                    return StringStore.del(parts[1]);
                case "ping":
                    return "pong";
                case "lpush":
                    if (parts.length < 3) return "ERROR: 参数不足";
                    return ListStore.lpush(parts[1], parts[2]);
                case "rpush":
                    if (parts.length < 3) return "ERROR: 参数不足";
                    return ListStore.rpush(parts[1], parts[2]);
                case "lpop":
                    if (parts.length < 2) return "ERROR: 需要指定key";
                    return ListStore.lpop(parts[1]);
                case "rpop":
                    if (parts.length < 2) return "ERROR: 需要指定key";
                    return ListStore.rpop(parts[1]);
                case "range":
                    if (parts.length < 4) return "ERROR: 需要start和end";
                    return ListStore.range(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                case "len":
                    if (parts.length < 2) return "ERROR: 需要指定key";
                    return ListStore.len(parts[1]);
                case "hset":
                    if (parts.length < 4) return "ERROR: 参数不足";
                    return HashStore.hset(parts[1], parts[2], parts[3]);
                case "hget":
                    if (parts.length < 3) return "ERROR: 需要指定field";
                    return HashStore.hget(parts[1], parts[2]);
                case "hdel":
                    if (parts.length < 3) return "ERROR: 需要指定field";
                    return HashStore.hdel(parts[1], parts[2]);
                default:
                    return "ERROR: 未知命令";
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}