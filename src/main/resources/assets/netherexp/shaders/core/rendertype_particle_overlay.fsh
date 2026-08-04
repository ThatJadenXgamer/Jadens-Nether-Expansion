#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D Sampler0;
uniform sampler2D SceneDepthBuffer;

uniform float DepthFade;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform mat4 InvProjMat;
uniform vec2 ScreenSize;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 viewSpacePos;

out vec4 fragColor;

vec3 overlayBlend(vec3 base, vec3 blend) {
    return mix(
        2.0 * base * blend,
        1.0 - 2.0 * (1.0 - base) * (1.0 - blend),
        step(0.5, base)
    );
}

void main() {
    vec2 screenUV = gl_FragCoord.xy / ScreenSize;

    vec4 texColor = texture(Sampler0, texCoord0);
    vec4 blendedColor = vec4(overlayBlend(texColor.rgb, vertexColor.rgb), texColor.a * vertexColor.a);
    blendedColor *= ColorModulator;

    vec4 fogged = applyFog(blendedColor, FogStart, FogEnd, FogColor, vertexDistance);
    if (fogged.a == 0.0) {
        discard;
    }
    fragColor = fogged;

    float sceneDepthClip = getDepth(SceneDepthBuffer, screenUV);
    vec3 sceneViewSpace = viewSpaceFromDepth(sceneDepthClip, screenUV, InvProjMat);

    float depthFade = applyDepthFade(sceneViewSpace.z, viewSpacePos.z, DepthFade);

    fragColor.a *= depthFade;
}