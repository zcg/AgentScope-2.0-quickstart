package cn.da.shuai.agentscope.agentscope2quickstart;

/**
 * Created on 15 6月 2026.
 *
 * @author github.com/zcg
 */
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.message.UserMessage;
import io.agentscope.extensions.model.openai.OpenAIChatModel;
import io.agentscope.harness.agent.HarnessAgent;
import io.agentscope.harness.agent.memory.compaction.CompactionConfig;
import java.nio.file.Paths;

public class FirstAgent {
    public static void main(String[] args) {
        try (HarnessAgent agent = HarnessAgent.builder()
                .name("note-taker")
                .sysPrompt("你是一个帮助用户做笔记的助手。")
                // 字符串形式由 ModelRegistry 解析 —— 自动读取 DASHSCOPE_API_KEY；
                // 切换其他厂商时改用 "openai:gpt-5.5"、"anthropic:claude-sonnet-4-5"、
                // "gemini:gemini-2.0-flash" 或 "ollama:llama3"。
                .model(OpenAIChatModel.builder()
                        .baseUrl("https://ollama.com/v1")
                        .apiKey("329d796d1d2e45348837cab2edee3e6b.mDaBRrXGiA5L42bMWmJAAYwU")
                        .modelName("minimax-m3:cloud")
                        .build())
                .workspace(Paths.get(".agentscope/workspace"))
                .compaction(CompactionConfig.builder()
                        .triggerMessages(30)
                        .keepMessages(10)
                        .build())
                .build()) {

            RuntimeContext ctx = RuntimeContext.builder()
                    .sessionId("demo-session")
                    .userId("alice")
                    .build();

            // 第一轮：自我介绍 + 当天的事
            agent.call(new UserMessage("我叫天宇，今天准备一个关于 ReAct 的技术分享。"), ctx).block();

            // 第二轮：同 sessionId，自动恢复上一轮状态后回答
            agent.call(new UserMessage("我叫什么？我今天要干什么？"), ctx).block();
        }
    }
}

