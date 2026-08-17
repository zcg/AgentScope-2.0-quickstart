package cn.da.shuai.agentscope.agentscope2quickstart;

import io.agentscope.core.message.Msg;
import io.agentscope.harness.agent.HarnessAgent;
import io.agentscope.extensions.model.openai.OpenAIChatModel;
/**
 * Created on 15 6月 2026.
 *
 * @author github.com/zcg
 */
public class QuickStart {

    public static void main(String[] args) {
        try {
            HarnessAgent harnessAgent = HarnessAgent.builder()
                    .name("quickstart")
                    .model(OpenAIChatModel.builder()
                            .baseUrl("https://ollama.com/v1")
                            .apiKey("329d796d1d2e45348837cab2edee3e6b.mDaBRrXGiA5L42bMWmJAAYwU")
                            .modelName("minimax-m3:cloud")
                            .build())
                    .build();
            Msg call = harnessAgent.call(Msg.builder().textContent("你是谁？").build()).block();
            System.out.println(call.getTextContent());
            System.out.println(call.getMetadata());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
